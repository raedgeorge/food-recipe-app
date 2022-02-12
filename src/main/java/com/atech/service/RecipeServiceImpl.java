package com.atech.service;

import com.atech.commands.RecipeCommand;
import com.atech.converters.RecipeCommandToRecipe;
import com.atech.converters.RecipeToRecipeCommand;
import com.atech.entity.Recipe;
import com.atech.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;
    private final RecipeCommandToRecipe recipeCommandToRecipe;
    private final RecipeToRecipeCommand recipeToRecipeCommand;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             RecipeCommandToRecipe recipeCommandToRecipe,
                             RecipeToRecipeCommand recipeToRecipeCommand) {

        this.recipeRepository = recipeRepository;
        this.recipeCommandToRecipe = recipeCommandToRecipe;
        this.recipeToRecipeCommand = recipeToRecipeCommand;
    }

    @Override
    @Transactional
    public List<Recipe> findAll() {

        List<Recipe> recipeList = new ArrayList<>();
        log.debug("inside the recipe service impl");
        recipeRepository.findAll()
                .iterator()
                .forEachRemaining(recipeList::add);

        return recipeList;
    }

    @Override
    @Transactional
    public Recipe findById(int id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

            Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);

            Recipe saved = recipeRepository.save(recipe);

         return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(int id) {

        Recipe recipe = recipeRepository.findById(id).orElse(null);

        return recipeToRecipeCommand.convert(recipe);
    }

    @Override
    @Transactional
    public void deleteById(int id) {
        recipeRepository.deleteById(id);
    }
}
