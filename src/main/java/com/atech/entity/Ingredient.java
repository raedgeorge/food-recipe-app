package com.atech.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@EqualsAndHashCode(exclude = {"recipe"})
@Setter
@Getter
@Entity
public class Ingredient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String description;
    private BigDecimal amount;

    @ManyToOne
    private Recipe recipe;

    @OneToOne(fetch = FetchType.EAGER)
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
