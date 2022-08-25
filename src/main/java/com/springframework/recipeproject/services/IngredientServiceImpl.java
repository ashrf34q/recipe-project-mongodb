package com.springframework.recipeproject.services;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.converters.IngredientCmdToIngredient;
import com.springframework.recipeproject.converters.IngredientToIngredientCmd;
import com.springframework.recipeproject.domain.Ingredients;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.exceptions.NotFoundException;
import com.springframework.recipeproject.repositories.RecipeRepository;
import com.springframework.recipeproject.repositories.UnitOfMeasureRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IngredientServiceImpl implements IngredientService {
	
	private final RecipeRepository recipeRepository;
	private final UnitOfMeasureRepository uomRepo;
	private final IngredientToIngredientCmd ingrToIngrCmd;
	private final IngredientCmdToIngredient ingrCmdToIngr;

	public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientToIngredientCmd ingrToIngrCmd, UnitOfMeasureRepository uomRepo, IngredientCmdToIngredient ingrCmdToIngr) {
		this.recipeRepository = recipeRepository;
		this.uomRepo = uomRepo;
		this.ingrToIngrCmd = ingrToIngrCmd;
		this.ingrCmdToIngr = ingrCmdToIngr;
	}

	@Override
	public IngredientsCommand findByIngIdAndRecipeId(String recipeId, String ingredientId) {

		Optional<Recipe> tempRecipe = recipeRepository.findById(recipeId);
		
		if(!tempRecipe.isPresent()) { log.error("recipe " + recipeId + " not found!"); }
		
		Recipe recipe = tempRecipe.get();
		
		Optional<IngredientsCommand> ingredientCmdOpt = recipe.getIngredients().stream()
				.filter(ingredient -> ingredient.getId().equals(ingredientId))
				.map(ingredient -> ingrToIngrCmd.convert(ingredient)).findFirst();
		
		if(!ingredientCmdOpt.isPresent()) { throw new NotFoundException("Ingredient " + ingredientId + " not found!");   }
		
		return ingredientCmdOpt.get();
		
	}

	@Transactional
	@Override
	public IngredientsCommand saveIngredientCommand(IngredientsCommand command) {
		Optional<Recipe> recipeOpt = recipeRepository.findById(command.getRecipeId());
		
		if(!recipeOpt.isPresent()) { //check if recipe is in the repo
			log.error("Recipe " + command.getRecipeId() + " not found!");
			return new IngredientsCommand();
		}
		else { //recipe was found in the repo
			Recipe recipe = recipeOpt.get();
			
			//extract the ingredient we want to update
			Optional<Ingredients> ingredientOpt = recipe
					.getIngredients()
					.stream()
					.filter(ingredient -> ingredient.getId().equals(command.getId()))
					.findFirst();
			
			//Check if that ingredient is in the recipe 
			if(ingredientOpt.isPresent()) {
				//if present, just update it
				
				Ingredients ingredientFound = ingredientOpt.get();
				ingredientFound.setDescription(command.getDescription());
				ingredientFound.setUom(uomRepo.findById(command.getUom().getId())
						.orElseThrow(() -> new RuntimeException("UOM NOT FOUND")));
				ingredientFound.setAmount(command.getAmount());
			}
			else {	
				//if ingredient not present, create a new ingredient with ingredient command properties and add it to recipe object
				recipe.addIngredient(ingrCmdToIngr.convert(command));
			}
			
			//save recipe to repo
			Recipe savedRecipe = recipeRepository.save(recipe);
			
			//return ingredient command after being saved to the repo
			Optional<Ingredients> savedIngrOpt = savedRecipe.getIngredients().stream()
					.filter(ingredients -> ingredients.getId().equals(command.getId()))
					.findFirst();
			
			//checking in case id wasn't saved when we saved ingredient to our recipe
			
			if(!savedIngrOpt.isPresent()) {
				savedIngrOpt = savedRecipe.getIngredients().stream()
						.filter(ingredients -> ingredients.getAmount().equals(command.getAmount()))
						.filter(ingredients -> ingredients.getDescription().equals(command.getDescription()))
						.filter(ingredients -> ingredients.getUom().getId().equals(command.getUom().getId()))
						.findFirst();
			}
			
			return ingrToIngrCmd.convert(savedIngrOpt.get());
		}
		
	} //end saveIngredientCmd

	@Override
	public void deleteIngrById(String recipeId, String ingrId) {
		log.debug("Deleting ingredient " + ingrId + " from recipe " + recipeId);
		
		Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
		
		//check if recipe is found
		if(recipeOpt.isPresent()) {
			log.debug("Found recipe!");
			
			Recipe recipe = recipeOpt.get();
			
			Optional<Ingredients> ingredientOpt = recipe.getIngredients().stream()
					.filter(ingredient -> ingredient.getId().equals(ingrId))
					.findFirst();
			
			//check if ingredient is found
			if(ingredientOpt.isPresent()) {
				log.debug("Found ingredient");
				
				Ingredients ingrToDelete = ingredientOpt.get();
				ingrToDelete.setRecipe(null); // This tells Hibernate to detach the ingredient from the recipe
				recipe.getIngredients().remove(ingredientOpt.get());
				recipeRepository.save(recipe);
			}
			else {
				log.debug("Ingredient " + ingrId + " wasn't found");
			}
		}
			else {
				log.debug("Recipe " + recipeId + " wasn't found");
			}
	} //end deleteByIngrId
	
	
}
