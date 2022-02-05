package com.atech.entity;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
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

    public Ingredient(String description,
                      BigDecimal amount,
                      MeasureUnit measureUnit) {

        this.description = description;
        this.amount = amount;
        this.measureUnit = measureUnit;
    }

}
