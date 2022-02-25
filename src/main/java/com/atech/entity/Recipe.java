package com.atech.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Document
public class Recipe {

    @Id
    private String id;
    private String description;
    private Integer prepTime;
    private Integer cookTime;
    private Integer servings;
    private String source;
    private String url;
    private String directions;
    private Byte[] image;
    private Notes notes;
    private List<Ingredient> ingredients = new ArrayList<>();
    private Difficulty difficulty;
    private List<Category> categories = new ArrayList<>();

    // associations
    public void setNotes(Notes notes) {
        if (notes != null) {
            this.notes = notes;
        }
    }

    // associations
    public Recipe addIngredient(Ingredient ingredient){
        this.ingredients.add(ingredient);
        return this;
    }

}
