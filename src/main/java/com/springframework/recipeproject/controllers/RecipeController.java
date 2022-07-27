package com.springframework.recipeproject.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.services.RecipeService;

@Controller
public class RecipeController {
	
	RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@RequestMapping("/recipe/{id}/show")
	public String getRecipe(@PathVariable String id, Model model){
		
		
		model.addAttribute("recipe", recipeService.findById(Long.parseLong(id)));
		
		return "recipe/show";
	}
	
	@RequestMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		
		return "recipe/recipeForm";
	}
	
//	@RequestMapping("/recipe/{id}/update")
//	public String updateRecipe(@PathVariable String id, Model model) {
//		model.addAttribute("recipe", recipeService)
//		
//	}
	
	@PostMapping
	@RequestMapping("recipe")
	public String saveRecipe(@ModelAttribute RecipeCommand recipeCommand) { //now recipeCommand is our model attribute for view
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
		
		return "redirect:/recipe/" + savedCommand.getId() + "/show" ;
	}

}
