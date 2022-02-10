package com.atech.service;

import com.atech.commands.IngredientCommand;

public interface IngredientService{

    IngredientCommand findByRecipeIdAndIngredientId(int recipeId, int ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
