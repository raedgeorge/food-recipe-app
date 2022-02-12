package com.atech.service;

import com.atech.commands.IngredientCommand;
import com.atech.entity.Ingredient;

public interface IngredientService{

    IngredientCommand findByRecipeIdAndIngredientId(int recipeId, int ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);

    Ingredient saveIngredient(Ingredient ingredient);

    void deleteIngredient(int recipeId, int ingredientId);
}
