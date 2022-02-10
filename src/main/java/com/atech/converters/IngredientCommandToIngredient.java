package com.atech.converters;

import com.atech.commands.IngredientCommand;
import com.atech.entity.Ingredient;
import com.atech.entity.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final MeasureUnitCommandToMeasureUnit command;
    private final MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand;

    public IngredientCommandToIngredient(MeasureUnitCommandToMeasureUnit command, MeasureUnitToMeasureUnitCommand measureUnitToMeasureUnitCommand) {
        this.command = command;
        this.measureUnitToMeasureUnitCommand = measureUnitToMeasureUnitCommand;
    }

    @Synchronized
    @Nullable
    @Override
    public Ingredient convert(IngredientCommand source) {

        if (source == null) {
            return null;
        }

        final  Ingredient ingredient = new Ingredient();

        ingredient.setId(source.getId());

        if (source.getRecipeId() != 0) {
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

            ingredient.setDescription(source.getDescription());
            ingredient.setAmount(source.getAmount());

            ingredient.setMeasureUnit(source.getMeasureUnit());

        return ingredient;
    }
}
