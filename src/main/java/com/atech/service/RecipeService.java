package com.atech.service;

import com.atech.entity.Recipe;

import java.util.List;

public interface RecipeService {

    List<Recipe> findAll();

    Recipe findById(int id);
}
