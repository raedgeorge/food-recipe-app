package com.atech.service;

import com.atech.entity.Food;

import java.util.List;

public interface CrudService<T, ID> {

    List<Food> findAll();

    Food findById(int id);


}
