package com.atech.commands;

import com.atech.entity.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private int id;
    private String description;
    private int prepTime;
    private int cookTime;
    private int servings;
    private String source;
    private String url;
    private String directions;
    private Difficulty difficulty;

    private NotesCommand notes;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private List<CategoryCommand> categories = new ArrayList<>();
}
