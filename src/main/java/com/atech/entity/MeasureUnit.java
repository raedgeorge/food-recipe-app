package com.atech.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Setter
@Getter
@Document
public class MeasureUnit {

    @Id
    private String id;
    private String unitOfMeasure;

}
