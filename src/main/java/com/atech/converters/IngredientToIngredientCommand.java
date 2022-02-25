package com.atech.converters;

import com.atech.commands.IngredientCommand;
import com.atech.entity.Ingredient;
import lombok.Synchronized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final MeasureUnitToMeasureUnitCommand command;

    public IngredientToIngredientCommand(
            MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand) {
        this.command = measureUnitToMeasureUnitCommand;
    }

    @Synchronized
    @Override
    public IngredientCommand convert(Ingredient ingredient) {

        if(ingredient == null) {
            return null;
        }

        final IngredientCommand ingredientCommand = new IngredientCommand();

        ingredientCommand.setId(String.valueOf(ingredient.getId()));

        if (ingredient.getRecipe() != null){
            ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        }
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
    // log.debug("before ingredient command setting measure unit");
    // ingredientCommand.setMeasureUnit(ingredient.getMeasureUnit());
        ingredientCommand.setMeasureUnitCommand(command.convert(ingredient.getMeasureUnit()));

        return ingredientCommand;
    }
}
