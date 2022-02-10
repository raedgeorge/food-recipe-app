package com.atech.converters;

import com.atech.commands.IngredientCommand;
import com.atech.entity.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientToIngredientCommand implements Converter<Ingredient, IngredientCommand> {

    private final MeasureUnitToMeasureUnitCommand command;
    private final MeasureUnitCommandToMeasureUnit measureUnitCommandToMeasureUnit;

    public IngredientToIngredientCommand(MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand, MeasureUnitCommandToMeasureUnit measureUnitCommandToMeasureUnit) {
        this.command = measureUnitToMeasureUnitCommand;
        this.measureUnitCommandToMeasureUnit = measureUnitCommandToMeasureUnit;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientCommand convert(Ingredient ingredient) {

        if(ingredient == null) {
            return null;
        }

        final IngredientCommand ingredientCommand = new IngredientCommand();

        ingredientCommand.setId(ingredient.getId());

        if (ingredient.getRecipe() != null){
            ingredientCommand.setRecipeId(ingredient.getRecipe().getId());
        }

        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setMeasureUnit(ingredient.getMeasureUnit());

        return ingredientCommand;
    }
}
