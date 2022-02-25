package com.atech.service;

import com.atech.commands.IngredientCommand;
import com.atech.entity.Ingredient;
import reactor.core.publisher.Mono;

public interface IngredientService{

    Mono<IngredientCommand> findByRecipeIdAndIngredientId(String recipeId, String ingredientId);

    Mono<IngredientCommand> saveIngredientCommand(String recipeId, IngredientCommand ingredientCommand);

    Ingredient saveIngredient(Ingredient ingredient);

    Mono<Void> deleteIngredient(String recipeId, String ingredientId);
}
