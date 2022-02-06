package com.atech.repositories;

import com.atech.entity.MeasureUnit;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class MeasureUnitRepositoryTestIT {

    @Autowired
    MeasureUnitRepository measureUnitRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByUnitOfMeasure() {

        Optional<MeasureUnit> measureUnit = measureUnitRepository.
                findByUnitOfMeasure("Cup");

        assertEquals("Cup", measureUnit.get().getUnitOfMeasure());
        }

    @Test
    public void findByUnitMeasureSpoons() {

        Optional<MeasureUnit> measureUnitOptional = measureUnitRepository.
                findByUnitOfMeasure("Table spoon");

        assertEquals("Table spoon", measureUnitOptional.get().getUnitOfMeasure());
    }
}