package com.springframework.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springframework.recipeproject.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
