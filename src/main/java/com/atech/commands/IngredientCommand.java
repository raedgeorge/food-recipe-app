package com.atech.commands;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class IngredientCommand {

    private String id;
    private String recipeId;
    private String description;
    private BigDecimal amount;
    private MeasureUnitCommand measureUnitCommand;

    public IngredientCommand() {
    }
}
