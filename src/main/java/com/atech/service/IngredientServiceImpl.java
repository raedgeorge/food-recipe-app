package com.atech.service;

import com.atech.commands.IngredientCommand;
import com.atech.converters.IngredientCommandToIngredient;
import com.atech.converters.IngredientToIngredientCommand;
import com.atech.entity.Ingredient;
import com.atech.entity.Recipe;
import com.atech.repositories.IngredientRepository;
import com.atech.repositories.reactive.MeasureUnitReactiveRepository;
import com.atech.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {

    private Ingredient savedIngredient;

    private final IngredientToIngredientCommand ingredientToIngredientCommand;
    private final IngredientCommandToIngredient ingredientCommandToIngredient;
    private final MeasureUnitReactiveRepository measureUnitReactiveRepository;
    private final RecipeReactiveRepository recipeReactiveRepository;
    private final IngredientRepository ingredientRepository;

    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand,
                                 IngredientCommandToIngredient ingredientCommandToIngredient,
                                 MeasureUnitReactiveRepository measureUnitReactiveRepository,
                                 RecipeReactiveRepository recipeReactiveRepository,
                                 IngredientRepository ingredientRepository) {

        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.measureUnitReactiveRepository = measureUnitReactiveRepository;
        this.recipeReactiveRepository = recipeReactiveRepository;
        this.ingredientRepository = ingredientRepository;

    }

    @Override
    public Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId) {

        return recipeReactiveRepository.findById(recipeId)
                .flatMapIterable(recipe -> recipe.getIngredients())
                .filter(ingredient -> ingredient.getId().equalsIgnoreCase(ingredientId))
                .single()
                .map(ingredient -> {
                    IngredientCommand command = ingredientToIngredientCommand.convert(ingredient);
                    command.setRecipeId(recipeId);
                    return command;
                });

//        Optional<Recipe> recipeOptional = recipeReactiveRepository.findById(recipeId).blockOptional();
//
//        if (!recipeOptional.isPresent()) {
//            log.error("recipe is not present >> " + recipeId);
//            return null;
//        }
//
//        Recipe recipe = recipeOptional.get();
//
//        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
//                .filter(ingredient -> ingredient.getId().equals(ingredientId))
//                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();
//
//        if (!ingredientCommandOptional.isPresent()) {
//            log.error("ingredient is not present >> " + ingredientId);
//            return null;
//        }
//
//        return Mono.just(ingredientCommandOptional.get());
    }

    @Override
    @Transactional
    public Mono<IngredientCommand> saveIngredientCommand(String recipeId, IngredientCommand ingredientCommand) {

        Recipe recipe = recipeReactiveRepository.findById(recipeId).block();

        log.debug("inside save ingredient command method >>> recipe check: " + recipe.getDescription());

        if (recipe == null) {
            return Mono.just(new IngredientCommand());
        }
        else {

            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription(ingredientCommand.getDescription());
                ingredientFound.setAmount(ingredientCommand.getAmount());

                ingredientFound.setMeasureUnit(measureUnitReactiveRepository
                        .findById(ingredientCommand.getMeasureUnitCommand().getId()).block());


                log.debug(">>> logger one ingredient unit measure >>>  " + ingredientFound.getDescription());

            } else {

                Ingredient ingredient = ingredientCommandToIngredient.convert(ingredientCommand);

                ingredient.setRecipe(recipe);
                ingredient.setDescription(ingredientCommand.getDescription());
                ingredient.setMeasureUnit(measureUnitReactiveRepository.
                        findByUnitOfMeasure(ingredientCommand.getMeasureUnitCommand()
                                .getDescription()).block());

                ingredient.setAmount(ingredientCommand.getAmount());

                savedIngredient = saveIngredient(ingredient);

                recipe.addIngredient(savedIngredient);
              //  log.debug(">>> logger two >>>  " + ingredientCommand.getMeasureUnitCommand().getDescription());
            }

            Recipe savedRecipe = recipeReactiveRepository.save(recipe).block();

            Optional<Ingredient> savedIngredientOptional = savedRecipe.getIngredients().stream()
                    .filter(recipeIngredient -> recipeIngredient.getId().equals(ingredientCommand.getId()))
                    .findFirst();

            log.info("saved ingredient optional result : " + savedIngredientOptional.get().getDescription());

            if(!savedIngredientOptional.isPresent()){
                savedIngredientOptional = savedRecipe.getIngredients().stream()
                        .filter(recipeIngredient -> recipeIngredient.getDescription().equals(ingredientCommand.getDescription()))
                        .filter(recipeIngredient -> recipeIngredient.getAmount().equals(ingredientCommand.getAmount()))
                        .filter(recipeIngredient -> recipeIngredient.getMeasureUnit().getId().equals(ingredientCommand.getMeasureUnitCommand().getId()))
                        .findFirst();

            }

            IngredientCommand savedIngredientCommand =  ingredientToIngredientCommand.convert(savedIngredientOptional.get());
            savedIngredientCommand.setRecipeId(recipe.getId());

            return Mono.just(savedIngredientCommand);
        }
    }

    @Override
    public Ingredient saveIngredient(Ingredient ingredient) {
       return ingredientRepository.save(ingredient);
    }

    @Override
    @Transactional
    public Mono<Void> deleteIngredient(String recipeId, String ingredientId) {

        log.error("====================================================");
        log.error("INSIDE THE DELETE INGREDIENT METHOD IMPLEMENTATION");
        log.error(ingredientId + " ::: " + recipeId);
        log.error("====================================================");

        Optional<Recipe> optionalRecipe = recipeReactiveRepository.findById(recipeId).blockOptional();
        log.error(">>>>> " + optionalRecipe.get().getDescription());

        if (optionalRecipe.isPresent()){

            Recipe recipe = optionalRecipe.get();

            Optional<Ingredient> found = recipe.getIngredients()
                  .stream()
                    .filter(ingredient -> ingredient.getId().equals(ingredientId))
                    .findFirst();

            if (found.isPresent()){
                recipe.getIngredients().remove(found.get());
                recipeReactiveRepository.save(recipe).block();
            }
        }
//
//        if (!ingredientOptional.isPresent()){
//            throw  new RuntimeException("ingredient not found");
//        }
//
//        else {
//
//            Ingredient ingredient = ingredientOptional.get();
//            ingredient.setRecipe(null);
//
//            Optional<Recipe> optionalRecipe = recipeRepository.findById(recipeId);
//
//            if (optionalRecipe.isPresent()){
//                Recipe recipe = optionalRecipe.get();
//                recipe.getIngredients().stream()
//                                .filter(ingredient1 -> recipe.getIngredients().remove(ingredient1));
//
//
//                ingredientRepository.delete(ingredient);
//            }
       // }
        return Mono.empty();
    }
}
