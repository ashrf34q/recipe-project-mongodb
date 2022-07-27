package com.springframework.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {
	
	RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping
	@RequestMapping("/recipe/{id}/show")
	public String getRecipe(@PathVariable String id, Model model){
		
		
		model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
		
		return "recipe/show";
	}
	
	@GetMapping
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeForm";
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		
		return "recipe/recipeForm";
	}
	
	@PostMapping
	@RequestMapping("recipe")
	public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand) { //now recipeCommand is our model attribute for view
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
		
		return "redirect:/recipe/" + savedCommand.getId() + "/show" ;
	}
	
	@GetMapping
	@RequestMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		log.debug("Deleting id: " + id);
		
		recipeService.deleteById(Long.valueOf(id));
		
		return "redirect:/";
		
	}

}
