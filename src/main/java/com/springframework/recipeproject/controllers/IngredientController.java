package com.springframework.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.recipeproject.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class IngredientController {
	
	private final RecipeService recipeService;

	public IngredientController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/ingredients")
	public String listIngredients(@PathVariable String id, Model model) {
		log.debug("Listing ingredients for recipe id: " + id);
		
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		return "recipe/ingredient/list";
	}
}
