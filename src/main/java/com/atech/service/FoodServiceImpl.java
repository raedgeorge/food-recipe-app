package com.atech.service;

import com.atech.entity.Food;

import java.util.ArrayList;
import java.util.List;

public class FoodServiceImpl implements FoodService<Food, Integer>{
    @Override
    public List<Food> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Food findById(int id) {
        return null;
    }
}
