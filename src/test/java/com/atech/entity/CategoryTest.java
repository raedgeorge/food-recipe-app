package com.atech.entity;

import org.junit.Before;

import static org.junit.Assert.assertEquals;

public class CategoryTest {

    Category category;

    @Before
    public void setUp(){

        category = new Category();
    }

    @org.junit.Test
    public void getId() {

        category.setId("5");
        assertEquals(String.valueOf(5), category.getId());
    }

    @org.junit.Test
    public void getName() {
    }

    @org.junit.Test
    public void getRecipes() {
    }
}