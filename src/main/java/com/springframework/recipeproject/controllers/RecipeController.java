package com.springframework.recipeproject.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
		
		return "recipe/recipeForm";
	} //end new Recipe
	
	@GetMapping("/recipe/{id}/update")
	public String updateRecipe(@PathVariable String id, Model model) {
		model.addAttribute("recipe", recipeService.findCommandById(Long.valueOf(id)));
		
		return "recipe/recipeForm";
	} //end updateRecipe
	
	@PostMapping("/recipe")
	public String saveRecipe(@ModelAttribute RecipeCommand command) { //now recipeCommand is our model attribute for view
		
		RecipeCommand savedCommand = recipeService.saveRecipeCommand(command);

		
		return "redirect:/recipe/" + savedCommand.getId() + "/show" ;
	}//end saveRecipe
	
	@GetMapping("/recipe/{id}/delete")
	public String deleteRecipe(@PathVariable String id) {
		log.debug("Deleting id: " + id);
		
		recipeService.deleteById(Long.valueOf(id));
		
		return "redirect:/";
		
	}//end deleteRecipe
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		
		log.error("Handling not found exception");
		log.error(exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("404Error");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleBadNumberFormat(Exception exception) {
		log.error("Handling Number format Exception");
		log.error(exception.getMessage());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("400Error");
		modelAndView.addObject("exception", exception);
		
		return modelAndView;
	}

}
