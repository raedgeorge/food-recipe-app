package com.atech.converters;

import com.atech.commands.RecipeCommand;
import com.atech.entity.Recipe;
import lombok.Synchronized;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class RecipeCommandToRecipe implements Converter<RecipeCommand, Recipe> {

    private final NotesCommandToNotes notesConverter;
    private final CategoryCommandToCategory categoryConverter;
    private final IngredientCommandToIngredient ingredientConverter;

    public RecipeCommandToRecipe(NotesCommandToNotes notesConverter,
                                 CategoryCommandToCategory categoryConverter,
                                 IngredientCommandToIngredient ingredientConverter) {

        this.notesConverter = notesConverter;
        this.categoryConverter = categoryConverter;
        this.ingredientConverter = ingredientConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public Recipe convert(RecipeCommand source) {

        if (source == null) {
            return null;
        }

        final Recipe recipe = new Recipe();

        recipe.setId(source.getId());
        recipe.setDescription(source.getDescription());
        recipe.setPrepTime(source.getPrepTime());
        recipe.setCookTime(source.getCookTime());
        recipe.setServings(source.getServings());
        recipe.setSource(source.getSource());
        recipe.setUrl(source.getUrl());
        recipe.setDirections(source.getDirections());
        recipe.setDifficulty(source.getDifficulty());

        recipe.setImage(source.getImage());

        recipe.setNotes(notesConverter.convert(source.getNotes()));

        if (source.getIngredients() != null && source.getIngredients().size() > 0){

            source.getIngredients().forEach(ingredientCommand ->
                    recipe.getIngredients().add(ingredientConverter.convert(ingredientCommand)) );
        }

        if (source.getCategories() != null && source.getCategories().size() > 0){

            source.getCategories().forEach(categoryCommand ->
                    recipe.getCategories().add(categoryConverter.convert(categoryCommand)));
        }

        return recipe;
    }
}
