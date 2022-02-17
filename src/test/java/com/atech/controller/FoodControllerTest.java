package com.atech.controller;

import com.atech.entity.Recipe;
import com.atech.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


public class FoodControllerTest {

    FoodController foodController;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        foodController = new FoodController(recipeService);
    }

    @Test
    public void testGetRecipeNumberFormatException() throws Exception{

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(foodController)
                                          .build();

        mockMvc.perform(get("/food/aa/recipe"))
                .andExpect(status().isBadRequest())
                .andExpect(view().name("errors/400error"));
    }

    @Test
    public void testMockMvc() throws Exception {
        MockMvc mockMvc = MockMvcBuilders.
                          standaloneSetup(foodController).build();

        mockMvc.perform(get("/food"))
                .andExpect(status().isOk())
                .andExpect(view().name("food/food-list"));
    }

    @Test
    public void getFoodList() {

        //given
        List<Recipe> recipes = new ArrayList<>();

        recipes.add(new Recipe());

        Recipe recipe = new Recipe();
        recipe.setId(2);
        recipes.add(recipe);

        when(recipeService.findAll()).thenReturn(recipes);

        ArgumentCaptor<List<Recipe>> argumentCaptor =
                ArgumentCaptor.forClass(List.class);

        String str = foodController.getFoodList(model);
        assertEquals(str, "food/food-list");

        verify(recipeService, times(1)).findAll();
        verify(model, times(1))
                  .addAttribute(eq("recipes"), argumentCaptor.capture());

        List<Recipe> inControllerList = argumentCaptor.getValue();
        assertEquals(2, inControllerList.size());

    }
}