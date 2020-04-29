package com.it.demo.repository;

import com.it.demo.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient,Long> {
    List<Ingredient> findIngredientsByRecipeId(Long id);
    Optional<Ingredient> findIngredientByRecipeIdAndId(Long recipeId, Long id);
}
