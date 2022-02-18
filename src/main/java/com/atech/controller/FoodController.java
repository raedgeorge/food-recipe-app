package com.atech.controller;

import com.atech.commands.RecipeCommand;
import com.atech.exceptions.NotFoundException;
import com.atech.service.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

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

//       try {
//           model.addAttribute("recipe", recipeService.findById(Integer.parseInt(id)));
//           return "food/recipe";
//       }
//       catch (NumberFormatException exception){
//           throw new BadNumberException(
//                   "value provided is bad. required int, provided a string, " + id);
//       }

        model.addAttribute("recipe", recipeService.findById(Integer.parseInt(id)));
           return "food/recipe";

    }

    @GetMapping("/addRecipe")
    public String addRecipe(Model model){

        model.addAttribute("recipe", new RecipeCommand());

        return RECIPE_RECIPEFORM_URL;
    }

    @PostMapping("/saveRecipe")
    public String saveRecipe(@Valid @ModelAttribute("recipe") RecipeCommand recipeCommand,
                             BindingResult bindingResult){

        if (bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            return RECIPE_RECIPEFORM_URL;
        }

        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/food/"+savedCommand.getId()+"/recipe/" ;
    }

    @GetMapping("/update/{id}")
    public String updateRecipe(Model model,
                               @PathVariable("id") int id){

        model.addAttribute("recipe", recipeService.findCommandById(id));

        return RECIPE_RECIPEFORM_URL;
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable("id") int id){

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
