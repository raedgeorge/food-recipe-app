package com.atech.controller;

import com.atech.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/food")
public class FoodController {

    private final RecipeService recipeService;

    @Autowired
    public FoodController(RecipeService recipeService) {
        log.debug("Inside the Food Controller");
        this.recipeService = recipeService;
    }

    @GetMapping({"", "/", "food-list", "food-list.html"})
    public String getFoodList(Model model){

        model.addAttribute("recipes", recipeService.findAll());
        log.info("Inside the Get Mapping for Index Page");

        return "food/food-list";
    }

    @GetMapping("/recipe/{id}")
    public String recipe(@PathVariable("id") int id,
                         Model model){

        model.addAttribute("recipe", recipeService.findById(id));

        return "food/recipe";
    }

}
