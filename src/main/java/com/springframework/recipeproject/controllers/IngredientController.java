package com.springframework.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.recipeproject.services.IngredientService;
import com.springframework.recipeproject.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable String id, Model model) {
		log.debug("Listing ingredients for recipe id: " + id);
		
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/ingredient/list";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		
		log.debug("Showing a specific ingredient based on its id");
		
		model.addAttribute("ingredient", ingredientService.findByIngIdAndRecipeId(Long.valueOf(recipeId), Long.valueOf(ingredientId)));
		
		return "recipe/ingredient/show";
		
	}
}
