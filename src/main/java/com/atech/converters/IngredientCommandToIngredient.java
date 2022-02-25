package com.atech.converters;

import com.atech.commands.IngredientCommand;
import com.atech.entity.Ingredient;
import com.atech.entity.Recipe;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    private final MeasureUnitCommandToMeasureUnit command;

    public IngredientCommandToIngredient(MeasureUnitCommandToMeasureUnit command) {
        this.command = command;
    }

    @Override
    public Ingredient convert(IngredientCommand source) {

        if (source == null) {
            return null;
        }

        final  Ingredient ingredient = new Ingredient();

        ingredient.setId(source.getId());

        if (source.getRecipeId() != null) {
            Recipe recipe = new Recipe();
            recipe.setId(source.getRecipeId());
            ingredient.setRecipe(recipe);
            recipe.addIngredient(ingredient);
        }

            ingredient.setDescription(source.getDescription());
            ingredient.setAmount(source.getAmount());

            ingredient.setMeasureUnit(command.convert(source.getMeasureUnitCommand()));

        return ingredient;
    }
}
