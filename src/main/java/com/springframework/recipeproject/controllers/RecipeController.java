package com.springframework.recipeproject.controllers;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.exceptions.NotFoundException;
import com.springframework.recipeproject.services.RecipeService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class RecipeController {
	
	private static final String RECIPE_RECIPEFORM_URL = "recipe/recipeForm";
	
	RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		this.recipeService = recipeService;
	}

	@GetMapping("/recipe/{id}/show")
	public String getRecipe(@PathVariable Long id, Model model){

		model.addAttribute("recipe", recipeService.findById(id));
		
		return "recipe/show";
	}//end getRecipe
	
	@GetMapping("/recipe/new")
	public String newRecipe(Model model) {
		model.addAttribute("recipe", new RecipeCommand());
		 
		return RECIPE_RECIPEFORM_URL;
	} //end new Recipe
	
	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		
		return RECIPE_RECIPEFORM_URL;
	} //end updateRecipe
	
	@PostMapping("/recipe")
	public String saveOrUpdateRecipe(@Valid @ModelAttribute("recipe") RecipeCommand command, BindingResult result) { //now recipeCommand is our model attribute for view
		
		if(result.hasErrors()) {
			result.getAllErrors().forEach(error -> {
				log.debug(error.getDefaultMessage());
				});
			return RECIPE_RECIPEFORM_URL;
			}
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		
		return "redirect:/recipe/" + savedCommand.getId() + "/show" ;
	}//end saveRecipe
	
	@GetMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		log.debug("Deleting id: " + id);
		
		recipeService.deleteById(Long.valueOf(id));
		
		return "redirect:/";
		
	}//end deleteRecipe
	

}
