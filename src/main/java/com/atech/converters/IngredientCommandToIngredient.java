package com.atech.converters;

import com.atech.commands.IngredientCommand;
import com.atech.entity.Ingredient;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IngredientCommandToIngredient implements Converter<IngredientCommand, Ingredient> {

    @Nullable
    @Synchronized
    @Override
    public Ingredient convert(IngredientCommand source) {

        if (source == null) {
            return null;
        }

        final  Ingredient ingredient = new Ingredient();
        ingredient.setId(source.getId());
        ingredient.setDescription(source.getDescription());
        ingredient.setAmount(source.getAmount());
        ingredient.setMeasureUnit(source.getMeasureUnit());
        return ingredient;
    }
}
