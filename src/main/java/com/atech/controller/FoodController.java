package com.atech.controller;

import com.atech.commands.RecipeCommand;
import com.atech.exceptions.NotFoundException;
import com.atech.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.bson.codecs.pojo.IdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Locale;

@Slf4j
@Controller
@RequestMapping("/food")
public class FoodController {

    private final RecipeService recipeService;
    private final String RECIPE_RECIPEFORM_URL = "food/recipeForm";

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
    public String recipe(@PathVariable("id") String id,
                         Model model){

        log.debug("ID >>>> : " +id);
        log.debug(recipeService.findById(id).getDescription().toUpperCase(Locale.ROOT));
        model.addAttribute("recipe", recipeService.findById(id));
           return "food/recipe";

    }

    @GetMapping("/addRecipe")
    public String addRecipe(Model model){

        model.addAttribute("recipe", new RecipeCommand());
        log.debug("inside the add new recipe");

        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("/saveRecipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
                             @RequestParam("id") String id,
                             BindingResult bindingResult){

        if (id == "") {
            recipeCommand.setId(IdGenerators.STRING_ID_GENERATOR.generate());
        }
        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return RECIPE_RECIPEFORM_URL;
        }

        log.debug("INSIDE THE SAVE RECIPE MAPPING");
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/food/"+savedCommand.getId()+"/recipe" ;
    }

    @GetMapping("/update/{id}")
    public String updateRecipe(Model model,
                               @PathVariable("id") String id){

        model.addAttribute("recipe", recipeService.findCommandById(id));

        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable("id") String id){

        log.debug("inside delete HTTP");
        recipeService.deleteById(id);
        return "redirect:/food/food-list";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public ModelAndView handleNotFound(Exception exception){

        log.debug("Handling not found exception");
        log.error(exception.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("errors/404error");
        modelAndView.addObject("exception", exception);
        return modelAndView;
    }

}
