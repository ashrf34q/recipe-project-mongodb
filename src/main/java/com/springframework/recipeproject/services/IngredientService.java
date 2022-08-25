package com.springframework.recipeproject.services;

import com.springframework.recipeproject.commands.IngredientsCommand;

public interface IngredientService {

	IngredientsCommand findByIngIdAndRecipeId(String recipeId, String ingredientId);

	IngredientsCommand saveIngredientCommand(IngredientsCommand command);

	void deleteIngrById(String recipeId, String ingrId);

}
