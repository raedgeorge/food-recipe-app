package com.atech.controller;

import com.atech.commands.RecipeCommand;
import com.atech.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping({"", "/", "/food-list", "/food-list.html"})
    public String getFoodList(Model model){

        model.addAttribute("recipes", recipeService.findAll());
        log.info("Inside the Get Mapping for Index Page");

        return "food/food-list";
    }

    @GetMapping("/{id}/recipe")
    public String recipe(@PathVariable("id") int id,
                         Model model){

        model.addAttribute("recipe", recipeService.findById(id));

        return "food/recipe";
    }

    @GetMapping("/addRecipe")
    public String addRecipe(Model model){

        model.addAttribute("recipe", new RecipeCommand());

        return "food/recipeForm";
    }

    @PostMapping("/saveRecipe")
    public String saveRecipe(@ModelAttribute("recipe") RecipeCommand recipeCommand){

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/food/"+savedCommand.getId()+"/recipe/" ;
    }

    @GetMapping("/update/{id}")
    public String updateRecipe(Model model,
                               @PathVariable("id") int id){

        model.addAttribute("recipe", recipeService.findCommandById(id));

        return "food/recipeForm";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable("id") int id){

        log.debug("inside delete HTTP");
        recipeService.deleteById(id);
        return "redirect:/food/food-list";
    }

}
