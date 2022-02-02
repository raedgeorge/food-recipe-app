package com.atech.controller;

import com.atech.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/food")
public class FoodController {

    private final RecipeService recipeService;

    @Autowired
    public FoodController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "food-list", "food-list.html"})
    public String getFoodList(Model model){

        model.addAttribute("recipes", recipeService.findAll());

        return "food/food-list";
    }

}
