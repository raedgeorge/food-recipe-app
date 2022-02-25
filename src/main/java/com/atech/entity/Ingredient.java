package com.atech.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigDecimal;
import java.util.UUID;

@Setter
@Getter
public class Ingredient {

    private String id = UUID.randomUUID().toString();
    private String description;
    private BigDecimal amount;

    private Recipe recipe;

    @DBRef
    private MeasureUnit measureUnit;

    public Ingredient() {
    }

    public Ingredient(String description, BigDecimal amount, Recipe recipe, MeasureUnit measureUnit) {
        this.description = description;
        this.amount = amount;
        this.recipe = recipe;
        this.measureUnit = measureUnit;
    }

    public Ingredient(String description, BigDecimal amount, MeasureUnit measureUnit) {
        this.description = description;
        this.amount = amount;
        this.measureUnit = measureUnit;
    }

}
