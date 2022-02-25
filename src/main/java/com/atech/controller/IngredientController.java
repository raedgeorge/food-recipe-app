package com.atech.controller;

import com.atech.commands.IngredientCommand;
import com.atech.commands.MeasureUnitCommand;
import com.atech.commands.RecipeCommand;
import com.atech.service.IngredientService;
import com.atech.service.RecipeService;
import com.atech.service.UnitOfMeasureService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@Slf4j
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitOfMeasureService unitOfMeasureService;

    @Autowired
    public IngredientController(RecipeService recipeService,
                                IngredientService ingredientService,
                                                 UnitOfMeasureService unitOfMeasureService) {

        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitOfMeasureService = unitOfMeasureService;
    }

    @GetMapping("/recipe/{recipeId}/ingredients")
    public String listIngredients(
            @PathVariable("recipeId") String id,
            Model model){

        RecipeCommand recipeCommand = recipeService.findCommandById(id);

        model.addAttribute("recipe", recipeCommand);
        return "/ingredients/list";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
    public String showIngredient(
            @PathVariable("recipeId") String recipeId,
            @PathVariable("ingredientId") String ingredientId,
            Model model){

        IngredientCommand ingredientCommand = ingredientService.
                findByRecipeIdAndIngredientId(recipeId, ingredientId).block();

        log.debug("INGREDIENT >>> " + ingredientCommand.getDescription());
        log.debug("RECIPE >>> " + ingredientCommand.getRecipeId());

        model.addAttribute("ingredient", ingredientCommand);

        return "/ingredients/show";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/update")
    public String updateIngredient(
            @PathVariable("recipeId") String recipeId,
            @PathVariable("ingredientId") String ingredientId,
            Model model, Model model2){

        IngredientCommand ingredientCommand = ingredientService.
                findByRecipeIdAndIngredientId(recipeId, ingredientId).block();

        log.error("DEBUGGING RECIPE ID VALUE : " + recipeId);
        model.addAttribute("ingredient", ingredientCommand);
        model.addAttribute("recipeId", recipeId);

        model2.addAttribute("uom", unitOfMeasureService.listAllUoms().collectList().block());

        return "/ingredients/update";
    }

    @PostMapping("/recipe/{recipeId}/ingredient")
    public String saveIngredient(
            @PathVariable String recipeId,
            @ModelAttribute("ingredient") IngredientCommand ingredientCommand){

//        log.error("INSIDE THE SAVE OR UPDATE METHOD");
//        log.error("INGREDIENT DATA " +ingredientCommand.getId() + " Recipe ID " + recipeId
//        + " " + ingredientCommand.getDescription());
//        log.error("DESCRIPTION >>> " + ingredientCommand.getDescription());
//        log.error("Selected MEASURE UNIT >>> " + ingredientCommand.getMeasureUnitCommand().getDescription());

        log.error("RECIPE ID TODAY: " + recipeId);
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(recipeId, ingredientCommand).block();

        return "redirect:/recipe/"+savedCommand.getRecipeId()+"/ingredient/"+savedCommand.getId()+"/show";

    }

    @GetMapping("recipe/{recipeId}/addIngredient")
    public String addIngredient(
            @PathVariable("recipeId") String recipeId,
            Model model1,
            Model model2){

        RecipeCommand recipeCommand = recipeService.findCommandById(recipeId);
        log.debug("Recipe Check: " + recipeCommand.getDescription());

        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setRecipeId(String.valueOf(recipeId));
        ingredientCommand.setMeasureUnitCommand(new MeasureUnitCommand());

        model1.addAttribute("ingredient", ingredientCommand);
        model2.addAttribute("uom", unitOfMeasureService.listAllUoms().collectList().block());
        log.debug("INSIDE ADD NEW INGREDIENT >>> RECIPE ID = " + recipeId);
        return "/ingredients/addIngredientForm";
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/delete")
    public String deleteIngredient(
            @PathVariable("recipeId") String recipeId,
            @PathVariable("ingredientId") String ingredientId){

        ingredientService.deleteIngredient(recipeId, ingredientId).block();

        return "redirect:/recipe/{recipeId}/ingredients";
    }
}
