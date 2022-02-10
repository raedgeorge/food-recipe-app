package com.atech.service;

import com.atech.commands.IngredientCommand;
import com.atech.converters.IngredientCommandToIngredient;
import com.atech.converters.IngredientToIngredientCommand;
import com.atech.entity.Ingredient;
import com.atech.entity.Recipe;
import com.atech.repositories.MeasureUnitRepository;
import com.atech.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {


    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final MeasureUnitRepository measureUnitRepository;
    private final RecipeRepository recipeRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 MeasureUnitRepository measureUnitRepository,
                                 RecipeRepository recipeRepository) {

        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.measureUnitRepository = measureUnitRepository;
        this.recipeRepository = recipeRepository;
    }

    @Override
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
                ingredientFound.setMeasureUnit(ingredientCommand.getMeasureUnit());
                ingredientFound.setAmount(ingredientCommand.getAmount());

                ingredientFound.setMeasureUnit(measureUnitRepository.
                        findById(ingredientCommand.
                        getMeasureUnit()
                        .getId())
                        .orElse(null));
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(ingredientCommand));
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            return ingredientToIngredientCommand.convert(savedRecipe.getIngredients().stream()
                    .filter(ingredient -> ingredient.getId() == ingredientCommand.getId())
                    .findFirst()
                    .get());

        }
    }
}
