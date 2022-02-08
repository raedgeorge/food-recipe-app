package com.atech.service;

import com.atech.commands.RecipeCommand;
import com.atech.entity.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe findById(int id);

    RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand);

    RecipeCommand findCommandById(int id);

    void delete(int id);
}
