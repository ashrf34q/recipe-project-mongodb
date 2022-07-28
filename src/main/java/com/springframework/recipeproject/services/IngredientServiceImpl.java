package com.springframework.recipeproject.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.converters.IngredientToIngredientCmd;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private final RecipeRepository recipeRepository;
	private final IngredientToIngredientCmd ingrToIngrCmd;

	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCmd ingrToIngrCmd) {
		this.recipeRepository = recipeRepository;
		this.ingrToIngrCmd = ingrToIngrCmd;
	}

	@Override
	public IngredientsCommand findByIngIdAndRecipeId(Long recipeId, Long ingredientId) {

		Optional<Recipe> tempRecipe = recipeRepository.findById(recipeId);
		
		if(!tempRecipe.isPresent()) { log.error("recipe " + recipeId + " not found!"); }
		
		Recipe recipe = tempRecipe.get();
		
		Optional<IngredientsCommand> ingredientCmdOpt = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingrToIngrCmd.convert(ingredient)).findFirst();
		
		if(!ingredientCmdOpt.isPresent()) { log.error("Ingredient " + ingredientId + " not found!");   }
		
		return ingredientCmdOpt.get();
		
	}

	@Override
	public IngredientsCommand saveIngredientCommand(IngredientsCommand command) {
		// TODO Auto-generated method stub
		return null;
	}
}
