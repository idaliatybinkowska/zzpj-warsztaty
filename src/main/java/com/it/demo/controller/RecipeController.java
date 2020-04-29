package com.it.demo.controller;

import com.it.demo.model.Ingredient;
import com.it.demo.model.Recipe;
import com.it.demo.repository.IngredientRepository;
import com.it.demo.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private IngredientRepository ingredientRepository;


    @GetMapping("/recipe/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent())
            return ResponseEntity.ok(recipe.get());
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/recipe/{id}")
    public ResponseEntity<Recipe> deleteRecipe(@PathVariable Long id){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()) {
            recipeRepository.deleteById(id);
            return ResponseEntity.ok(recipe.get());
        }
        else
            return ResponseEntity.notFound().build();
    }

    @GetMapping("/recipe/ingredients/{id}")
    public List<Ingredient> getIngredientsById(@PathVariable Long id){
        return ingredientRepository.findIngredientsByRecipeId(id);
    }

    @GetMapping("/recipe/{recipeId}/ingredient/{id}")
    public ResponseEntity<Ingredient> getIngredientByRecipeIdAndId(@PathVariable Long recipeId, @PathVariable Long id){
        Optional<Ingredient> ingredient = ingredientRepository.findIngredientByRecipeIdAndId(recipeId, id);
        if(ingredient.isPresent()) {
            return ResponseEntity.ok(ingredient.get());
        }
        else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/recipe/{recipeId}/ingredient/{id}")
    public ResponseEntity<Ingredient> deleteIngredientByRecipeIdAndId(@PathVariable Long recipeId, @PathVariable Long id){
        Optional<Ingredient> ingredient = ingredientRepository.findIngredientByRecipeIdAndId(recipeId, id);
        if(ingredient.isPresent()) {
            ingredientRepository.delete(ingredient.get());
            return ResponseEntity.ok(ingredient.get());
        }
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping("/recipe/{id}/ingredient")
    public ResponseEntity<Recipe> addIngredientToRecipe(@PathVariable Long id, @Valid @RequestBody Ingredient ingredient){
        Optional<Recipe> recipe = recipeRepository.findById(id);
        if(recipe.isPresent()) {
            ingredient.setRecipe(recipe.get());
            ingredientRepository.save(ingredient);
            return ResponseEntity.ok(recipe.get());
        }
        else
            return ResponseEntity.notFound().build();
    }
}

