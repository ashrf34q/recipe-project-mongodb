package com.springframework.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.commands.UoMCommand;
import com.springframework.recipeproject.services.IngredientService;
import com.springframework.recipeproject.services.RecipeService;
import com.springframework.recipeproject.services.UoMService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IngredientController {
	
	private final RecipeService recipeService;
	private final IngredientService ingredientService;
	private final UoMService uomService;

	public IngredientController(RecipeService recipeService, IngredientService ingredientService, UoMService uomService) {
		this.recipeService = recipeService;
		this.ingredientService = ingredientService;
		this.uomService = uomService;
	}
	
	@GetMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable String id, Model model) {
		log.debug("Listing ingredients for recipe id: " + id);
		
		model.addAttribute("recipe", recipeService.findCommandById(id));
		return "recipe/ingredient/list";
	}
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingredientId}/show")
	public String showIngredient(@PathVariable String recipeId, @PathVariable String ingredientId, Model model) {
		
		log.debug("Showing a specific ingredient based on its id");
		
		model.addAttribute("ingredient", ingredientService.findByIngIdAndRecipeId(recipeId, ingredientId));
		
		return "recipe/ingredient/show";
	}
	
	
	@GetMapping("/recipe/{recipeId}/ingredient/{id}/update")
	public String updateIngredient(@PathVariable String recipeId, @PathVariable String id, Model model) {
		
		model.addAttribute("ingredient", ingredientService.findByIngIdAndRecipeId(recipeId, id));
		
		model.addAttribute("uomList", uomService.listAllUoms());
		
		return "recipe/ingredient/ingredientForm";
	}
	
	
	@GetMapping("/recipe/{recipeId}/ingredient/new")
	public String newRecipe(@PathVariable String recipeId, Model model) {
		
		RecipeCommand recipeCmd = recipeService.findCommandById(recipeId);
		
		IngredientsCommand ingredientCmd = new IngredientsCommand();
		ingredientCmd.setRecipeId(recipeCmd.getId());
		
		model.addAttribute("ingredient", ingredientCmd);
		
		ingredientCmd.setUom(new UoMCommand());
		
		model.addAttribute("uomList", uomService.listAllUoms());
		
		return "recipe/ingredient/ingredientForm";
	} //end newRecipe
	
	
	@PostMapping("/recipe/{recipeId}/ingredient")
	public String saveOrUpdate(@ModelAttribute IngredientsCommand command) {
		IngredientsCommand savedCmd = ingredientService.saveIngredientCommand(command);
		
		log.debug("Saved recipe " + savedCmd.getRecipeId());
		log.debug("Saved Ingredient " + savedCmd.getId());
		
		return "redirect:/recipe/" + savedCmd.getRecipeId() + "/ingredients";
	} //end saveOrUpdate
	
	
	@GetMapping("/recipe/{recipeId}/ingredient/{ingrId}/delete")
	public String deleteIngredient(@PathVariable String recipeId, @PathVariable String ingrId) {
		
		ingredientService.deleteIngrById(recipeId, ingrId);
		
		return "redirect:/recipe/" + recipeId + "/ingredients";
	}
}
