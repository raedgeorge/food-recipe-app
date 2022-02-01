package com.atech.service;

import com.atech.entity.Recipe;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService<Recipe, Integer>{
    @Override
    public List<Recipe> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Recipe findById(int id) {
        return null;
    }
}
