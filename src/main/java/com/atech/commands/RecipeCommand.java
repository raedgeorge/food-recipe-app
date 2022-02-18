package com.atech.commands;

import com.atech.entity.Difficulty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {

    private int id;

    @NotBlank
    @Size(min = 3, max = 255)
    private String description;

    @Min(1)
    @Max(300)
    private int prepTime;

    @Min(1)
    @Max(300)
    private int cookTime;

    @Min(1)
    @Max(100)
    private int servings;


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
}
