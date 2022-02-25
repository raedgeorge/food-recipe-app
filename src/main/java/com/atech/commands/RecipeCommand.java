package com.atech.commands;

import com.atech.entity.Difficulty;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class RecipeCommand {

    private String id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(300)
    private Integer prepTime;


    @Min(1)
    @Max(300)
    private Integer cookTime;


    @Min(1)
    @Max(100)
    private Integer servings;

    private String source;

    @URL(message = "PLEASE PROVIDE A VALID URL ADDRESS")
    @NotBlank
    private String url;

    @NotBlank
    private String directions;

    private Difficulty difficulty;
    private Byte[] image;
    private NotesCommand notes;
    private List<IngredientCommand> ingredients = new ArrayList<>();
    private List<CategoryCommand> categories = new ArrayList<>();


    public RecipeCommand() {
    }

    public String getId() {
        return this.id;
    }

    public String getDescription() {
        return this.description;
    }

    public Integer getPrepTime() {
        return this.prepTime;
    }

    public Integer getCookTime() {
        return this.cookTime;
    }

    public Integer getServings() {
        return this.servings;
    }

    public String getSource() {
        return this.source;
    }

    public String getUrl() {
        return this.url;
    }

    public String getDirections() {
        return this.directions;
    }

    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    public Byte[] getImage() {
        return this.image;
    }

    public NotesCommand getNotes() {
        return this.notes;
    }

    public List<IngredientCommand> getIngredients() {
        return this.ingredients;
    }

    public List<CategoryCommand> getCategories() {
        return this.categories;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDescription(@NotBlank @Size(min = 3, max = 255) String description) {
        this.description = description;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public void setCookTime(Integer cookTime) {
        this.cookTime = cookTime;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setImage(Byte[] image) {
        this.image = image;
    }

    public void setNotes(NotesCommand notes) {
        this.notes = notes;
    }

    public void setIngredients(List<IngredientCommand> ingredients) {
        this.ingredients = ingredients;
    }

    public void setCategories(List<CategoryCommand> categories) {
        this.categories = categories;
    }
}
