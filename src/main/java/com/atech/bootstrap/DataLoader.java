package com.atech.bootstrap;

import com.atech.entity.*;
import com.atech.repositories.CategoryRepository;
import com.atech.repositories.MeasureUnitRepository;
import com.atech.repositories.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;

@Component
public class DataLoader implements CommandLineRunner {

    private final RecipeRepository recipeRepository;
    private final CategoryRepository categoryRepository;
    private final MeasureUnitRepository measureUnitRepository;

    @Autowired
    public DataLoader(RecipeRepository recipeRepository,
                      CategoryRepository categoryRepository,
                      MeasureUnitRepository measureUnitRepository) {

        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.measureUnitRepository = measureUnitRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        Recipe guacamole = new Recipe();
        guacamole.setDescription("Perfect Guacamole");
        guacamole.setPrepTime(10);
        guacamole.setCookTime(2);
        guacamole.setServings(4);
        guacamole.setSource("simply recipes");
        guacamole.setUrl("https://www.simplyrecipes.com/recipes/perfect_guacamole/");

        guacamole.setDirections("Cut the avocados in half. Remove the pit. Score the inside" +
                " of the avocado with a blunt knife and scoop out the flesh with a spoon." +
                " (See How to Cut and Peel an Avocado.) Place in a bowl");


        Notes note = new Notes();
        note.setNotes("Chilling tomatoes hurts their flavor. " +
                      "So, if you want to add chopped tomato to your guacamole, " +
                       "add it just before serving");

        guacamole.setNotes(note);
        note.setRecipe(guacamole);

        Ingredient avocado = new Ingredient();
        avocado.setRecipe(guacamole);
        avocado.setDescription("ripe avocados");
        avocado.setAmount(new BigDecimal(2));
        avocado.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("ripe").get());

        Ingredient salt = new Ingredient();
        salt.setRecipe(guacamole);
        salt.setDescription("white salt");
        salt.setAmount(new BigDecimal("0.25"));
        salt.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("Tea spoon").get());

        Ingredient lemon = new Ingredient();
        lemon.setRecipe(guacamole);
        lemon.setDescription("fresh lime juice or lemon juice");
        lemon.setAmount(new BigDecimal("1"));
        lemon.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("Table spoon").get());

        Ingredient onion = new Ingredient();
        onion.setRecipe(guacamole);
        onion.setDescription("minced red onion or thinly sliced green onion");
        onion.setAmount(new BigDecimal("4"));
        onion.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("Table spoon").get());

        Ingredient serrano = new Ingredient();
        serrano.setRecipe(guacamole);
        serrano.setDescription("chili's, stems and seeds removed, minced");
        serrano.setAmount(new BigDecimal("2"));
        salt.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("serrano").get());


        Ingredient cilantro = new Ingredient();
        cilantro.setRecipe(guacamole);
        cilantro.setDescription("leaves and tender stems, finely chopped");
        cilantro.setAmount(new BigDecimal("2"));
        cilantro.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("Table spoon").get());

        Ingredient pepper = new Ingredient();
        pepper.setRecipe(guacamole);
        pepper.setDescription(" ground black pepper");
        pepper.setAmount(new BigDecimal("0.5"));
        pepper.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("Pinch").get());

        Ingredient tomato = new Ingredient();
        tomato.setRecipe(guacamole);
        tomato.setDescription("chopped (optional)\n" +
                " Red radish or jicama slices for garnish (optional)");
        tomato.setAmount(new BigDecimal("0.5"));
        tomato.setMeasureUnit(measureUnitRepository.findByUnitOfMeasure("ripe").get());

        guacamole.setDifficulty(Difficulty.EASY);

        Optional<Category> americanCategory = categoryRepository.findByName("American");
        Optional<Category> mexicanCategory = categoryRepository.findByName("Mexican");
        guacamole.getCategories().add(americanCategory.get());
        guacamole.getCategories().add(mexicanCategory.get());

        guacamole.getIngredients().add(avocado);
        guacamole.getIngredients().add(salt);
        guacamole.getIngredients().add(lemon);
        guacamole.getIngredients().add(onion);
        guacamole.getIngredients().add(serrano);
        guacamole.getIngredients().add(cilantro);
        guacamole.getIngredients().add(pepper);
        guacamole.getIngredients().add(tomato);

        recipeRepository.save(guacamole);
    }
}
