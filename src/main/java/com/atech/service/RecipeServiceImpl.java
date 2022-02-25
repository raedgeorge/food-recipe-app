package com.atech.service;

import com.atech.commands.RecipeCommand;
import com.atech.converters.RecipeCommandToRecipe;
import com.atech.converters.RecipeToRecipeCommand;
import com.atech.entity.Recipe;
import com.atech.exceptions.NotFoundException;
import com.atech.repositories.RecipeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Recipe findById(String id) {

        Optional<Recipe> recipe = recipeRepository.findById(id);

        if (!recipe.isPresent()){
            throw new NotFoundException("Recipe Not Found. ID value: " + id);
        }

        return recipe.get();
    }

    @Override
    @Transactional
    public RecipeCommand saveRecipeCommand(RecipeCommand recipeCommand) {

        log.debug("inside the recipe service impl saving recipe command");

            Recipe recipe = recipeCommandToRecipe.convert(recipeCommand);


            if (recipe !=null) {
                Recipe saved = recipeRepository.save(recipe);
                return recipeToRecipeCommand.convert(saved);
            }

            return null;
    }

    @Override
    @Transactional
    public RecipeCommand findCommandById(String id) {

        return recipeToRecipeCommand.convert(findById(id));
    }

    @Override
    @Transactional
    public void deleteById(String id) {
        recipeRepository.deleteById(id);
    }
}
