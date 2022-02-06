package com.atech.service;

import com.atech.entity.Recipe;
import com.atech.repositories.RecipeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class RecipeServiceImplTest {

    RecipeServiceImpl recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.openMocks(this);
        recipeService  =new RecipeServiceImpl(recipeRepository);
    }

    @Test
    public void findAll() {

        Recipe recipe = new Recipe();
        List recipeData = new ArrayList();
        recipeData.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeData);
        List<Recipe> recipes  = recipeService.findAll();
        assertEquals(recipes.size(), 1);

        verify(recipeRepository, times(1)).findAll();

    }
}