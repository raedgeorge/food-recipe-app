package com.atech.repositories.reactive;

import com.atech.entity.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@DataMongoTest
class RecipeReactiveRepositoryTest {

    @Autowired
    RecipeReactiveRepository reactiveRepository;

    @BeforeEach
    public void setUp() throws Exception {
        reactiveRepository.deleteAll().block();
    }

    @Test
    public void testRecipeSave() throws Exception {

        Recipe recipe = new Recipe();
        recipe.setDescription("Hamburger");

        reactiveRepository.save(recipe).block();
        Long count = reactiveRepository.count().block();
        assertEquals(Long.valueOf(1L), count);
    }
}