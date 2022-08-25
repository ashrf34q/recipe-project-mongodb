package com.springframework.recipeproject.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.converters.RecipeCmdToRecipe;
import com.springframework.recipeproject.converters.RecipeToRecipeCmd;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.exceptions.NotFoundException;
import com.springframework.recipeproject.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
	
	private final RecipeRepository recipeRepository;
	private final RecipeCmdToRecipe recipeCmdToRecipe;
	private final RecipeToRecipeCmd recipeToRecipeCmd;
	
		public RecipeServiceImpl(RecipeRepository recipeRepository, RecipeToRecipeCmd recipeToRecipeCmd, RecipeCmdToRecipe recipeCmdToRecipe) {
		this.recipeRepository = recipeRepository;
		this.recipeCmdToRecipe = recipeCmdToRecipe;
		this.recipeToRecipeCmd = recipeToRecipeCmd;
	}

	@Override
	public Set<Recipe> getRecipes() {
		log.debug("I'm in the service");
		
		Set<Recipe> recipeSet = new HashSet<>();
		recipeRepository.findAll().iterator().forEachRemaining(recipeSet::add);
		
		return recipeSet;
	}

	@Override
	public Recipe findById(String id) {
		Optional<Recipe> recipeOptional = recipeRepository.findById(id);
		
		if(!recipeOptional.isPresent())
		{
//			throw new RuntimeException("Recipe not found");
			throw new NotFoundException("Recipe " +  id + " not found!" );
		}
		
		return recipeOptional.get();
	}

	
	@Override
	@Transactional
	public RecipeCommand saveRecipeCommand(RecipeCommand command) {
		Recipe detachedRecipe = recipeCmdToRecipe.convert(command);  //detached from hibernate context
		
		Recipe savedRecipe = recipeRepository.save(detachedRecipe);
		log.debug("Saved Recipe: " + savedRecipe.getId());
		return recipeToRecipeCmd.convert(savedRecipe);		
	}

	@Transactional
	@Override
	public RecipeCommand findCommandById(String id) {
		return recipeToRecipeCmd.convert(findById(id));
	}

	@Override	
	public void deleteById(String id) {
		recipeRepository.deleteById(id);
	}

}
