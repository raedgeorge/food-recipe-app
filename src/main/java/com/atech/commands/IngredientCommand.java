package com.atech.commands;

import com.atech.entity.MeasureUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class IngredientCommand {

    private int id;
    private String description;
    private BigDecimal amount;
    private MeasureUnit measureUnit;

}
