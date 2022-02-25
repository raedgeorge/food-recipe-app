package com.atech.controller;

import com.atech.commands.RecipeCommand;
import com.atech.service.ImageService;
import com.atech.service.RecipeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

class ImageControllerTest {

    ImageController imageController;

    @Mock
    ImageService imageService;

    @Mock
    RecipeService recipeService;

    @Mock
    Model model;

    @BeforeEach
    void setUp() throws Exception{
        MockitoAnnotations.openMocks(this);
        imageController = new ImageController(
                recipeService, imageService);
    }

    @Test
    void imageUploadForm() throws Exception{

        RecipeCommand command = new RecipeCommand();
        command.setId("1");

        when(recipeService
                .findCommandById(anyString())).thenReturn(command);


        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(imageController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();

        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(view().
                        name("/image/imageUploadForm"));

        verify(recipeService, times(1))
                .findCommandById(anyString());
    }

    @Test
    void handleImagePost() {
    }

    @Test
    void renderImageFromDB() {
    }
}