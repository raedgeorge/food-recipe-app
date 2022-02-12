package com.atech.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class IngredientCommand {

    private int id;
    private int recipeId;
    private String description;
    private BigDecimal amount;
    private MeasureUnitCommand measureUnitCommand;

}
