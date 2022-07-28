package com.springframework.recipeproject.services;

import com.springframework.recipeproject.commands.IngredientsCommand;

public interface IngredientService {

	IngredientsCommand findByIngIdAndRecipeId(Long recipeId, Long ingredientId);

	IngredientsCommand saveIngredientCommand(IngredientsCommand command);

}
