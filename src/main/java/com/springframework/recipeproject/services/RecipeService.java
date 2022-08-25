package com.springframework.recipeproject.services;

import java.util.Set;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.domain.Recipe;

public interface RecipeService {
	Set<Recipe> getRecipes();

	Recipe findById(String id);
	
	RecipeCommand saveRecipeCommand(RecipeCommand command);

	RecipeCommand findCommandById(String id);
	
	void deleteById(String id);
}
