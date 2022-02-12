package com.atech.service;

import com.atech.commands.IngredientCommand;
import com.atech.converters.IngredientCommandToIngredient;
import com.atech.converters.IngredientToIngredientCommand;
import com.atech.converters.MeasureUnitCommandToMeasureUnit;
import com.atech.entity.Ingredient;
import com.atech.entity.MeasureUnit;
import com.atech.entity.Recipe;
import com.atech.repositories.IngredientRepository;
import com.atech.repositories.MeasureUnitRepository;
import com.atech.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private Ingredient savedIngredient;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final MeasureUnitRepository measureUnitRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 MeasureUnitRepository measureUnitRepository,
                                 RecipeRepository recipeRepository,
                                 IngredientRepository ingredientRepository,
                                 MeasureUnitCommandToMeasureUnit measureUnitCommandToMeasureUnit) {

        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.measureUnitRepository = measureUnitRepository;
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
    }

    @Override
    @Transactional
    public IngredientCommand findByRecipeIdAndIngredientId(int recipeId, int ingredientId) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        if (!recipeOptional.isPresent()) {
            log.error("recipe is not present >> " + recipeId);
            return null;
        }

        Recipe recipe = recipeOptional.get();

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId() == ingredientId)
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        if (!ingredientCommandOptional.isPresent()) {
            log.error("ingredient is not present >> " + ingredientId);
            return null;
        }

        return ingredientCommandOptional.get();
    }

    @Override
    @Transactional
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> optionalRecipe = recipeRepository.findById(ingredientCommand.getRecipeId());

        log.debug("inside save ingredient command method >>> recipe check: " + optionalRecipe.get().getDescription());

        if (!optionalRecipe.isPresent()) {
            return new IngredientCommand();
        }
        else {
            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId() == ingredientCommand.getId())
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());

                ingredientFound.setMeasureUnit(measureUnitRepository
                        .findByUnitOfMeasure(ingredientCommand.getMeasureUnitCommand().getDescription())
                        .orElse(new MeasureUnit()));

                log.debug(">>> logger one ingredient unit measure >>>  " + ingredientFound.getDescription());

            } else {

                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

                ingredient.setRecipe(recipe);
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure(ingredientCommand.getMeasureUnitCommand().getDescription()).get());
                ingredient.setAmount(ingredientCommand.getAmount());

                savedIngredient = saveIngredient(ingredient);

                log.debug("SAVED INGREDIENT Text: " + savedIngredient.getDescription() + ", ID: " + savedIngredient.getId());
                log.debug("SAVED INGREDIENT Amount: " + savedIngredient.getAmount() + ", UOM: " + savedIngredient.getMeasureUnit().getUnitOfMeasure());

                recipe.addIngredient(savedIngredient);
              //  log.debug(">>> logger two >>>  " + ingredientCommand.getMeasureUnitCommand().getDescription());
            }

            Recipe savedRecipe = recipeRepository.save(recipe);

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getId() == savedIngredient.getId())
                    .findFirst();

            log.info("saved ingredient optional result : " + savedIngredientOptional.get().getDescription());

            if(!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getMeasureUnit().getId() == ingredientCommand.getMeasureUnitCommand().getId())
                        .findFirst();

            }
            log.debug("reached here===============");
            IngredientCommand ingredientCommand1 =  ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            log.debug("ingredient command result >>> " + ingredientCommand1.getDescription());
            log.debug("ingredient command uom >>> " + ingredientCommand1.getMeasureUnitCommand().getDescription());
            log.debug("ingredient command amount  >>> " + ingredientCommand1.getAmount());
            log.debug("ingredient command ID " + ingredientCommand1.getId());
            log.debug("reached here===============");

            return ingredientCommand1;
        }
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
       return ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional
    public void deleteIngredient(int recipeId, int ingredientId) {

        Optional<Ingredient> ingredientOptional = ingredientRepository.findById(ingredientId);

        if (!ingredientOptional.isPresent()){
            throw  new RuntimeException("ingredient not found");
        }

        else {

            Ingredient ingredient = ingredientOptional.get();
            ingredient.setRecipe(null);

            Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);

            if (optionalRecipe.isPresent()){
                Recipe recipe = optionalRecipe.get();
                recipe.getIngredients().stream()
                                .filter(ingredient1 -> recipe.getIngredients().remove(ingredient1));


                ingredientRepository.delete(ingredient);
            }
        }
    }
}
