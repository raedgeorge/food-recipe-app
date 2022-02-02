package com.atech.service;

import com.atech.entity.Recipe;
import com.atech.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService{

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public List<Recipe> findAll() {

        List<Recipe> recipeList = new ArrayList<>();

        recipeRepository.findAll()
                .iterator()
                .forEachRemaining(recipe -> recipeList.add(recipe));

        return recipeList;
    }
}
