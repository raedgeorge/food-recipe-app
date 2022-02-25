package com.atech.repositories.reactive;

import com.atech.entity.MeasureUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
class MeasureUnitReactiveRepositoryTest {

    public static final String EACH = "Each";

    @Autowired
    MeasureUnitReactiveRepository measureUnitReactiveRepository;

    @BeforeEach
    void setUp() {
        measureUnitReactiveRepository.deleteAll().block();
    }

    @Test
    public void testSaveUom(){

        MeasureUnit measureUnit = new MeasureUnit();
        measureUnit.setUnitOfMeasure(EACH);

        measureUnitReactiveRepository.save(measureUnit).block();
        Long count = measureUnitReactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }

    @Test
    public void testFindByName(){

        MeasureUnit measureUnit = new MeasureUnit();
        measureUnit.setUnitOfMeasure(EACH);

        measureUnitReactiveRepository.save(measureUnit).block();

        MeasureUnit unit = measureUnitReactiveRepository.findByUnitOfMeasure(EACH).block();
        assertEquals("Each", unit.getUnitOfMeasure());

    }
}