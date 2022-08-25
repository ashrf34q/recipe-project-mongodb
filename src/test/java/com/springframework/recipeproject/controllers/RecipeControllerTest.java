package com.springframework.recipeproject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.exceptions.NotFoundException;
import com.springframework.recipeproject.services.RecipeService;

class RecipeControllerTest {

	@Mock
	RecipeService recipeService;
	
	RecipeController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		controller = new RecipeController(recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller)
				.setControllerAdvice(new ControllerExceptionHandler()).build();
	
	}

	@Test
	void testGetRecipe() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		when(recipeService.findById(anyString())).thenReturn(recipe);
		
		mockMvc.perform(get("/recipe/1/show"))
				.andExpect(status().isOk())
				.andExpect(view().name("recipe/show"))
				.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void newRecipeTest() throws Exception {
//		RecipeCommand recipeCommand = new RecipeCommand();
		
		mockMvc.perform(get("/recipe/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeForm"))
		.andExpect(model().attributeExists("recipe"));
	}
	
	@Test
	public void saveRecipeTest() throws Exception {
		RecipeCommand recipeCommand = new RecipeCommand();
		recipeCommand.setId("1");
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(recipeCommand);
		
		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED)
				.param("id", "")
				.param("description", "some string")
				.param("directions", "some directions")
				.param("servings", "13")
				.param("cookTime", "23")
				.param("prepTime", "10"))
		
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/1/show"));
	}
	
	@Test
	void testPostNewRecipeFormValidationFail() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");
		
		when(recipeService.saveRecipeCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe").contentType(MediaType.APPLICATION_FORM_URLENCODED).param("id", ""))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("recipe"))
		.andExpect(view().name("recipe/recipeForm"));
	}
	
	@Test
	public void updateRecipeTest() throws Exception {
		RecipeCommand command = new RecipeCommand();
		command.setId("2");

		when(recipeService.findCommandById(anyString())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/2/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/recipeForm"))
		.andExpect(model().attributeExists("recipe"));
		
	}
	
	@Test
	public void deleteRecipeTest() throws Exception {
		mockMvc.perform(get("/recipe/1/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/"));
		
		verify(recipeService, times(1)).deleteById(anyString());
		
	}
	
	@Test
	void testGetRecipeNotFound() throws Exception {
		
		when(recipeService.findById(anyString())).thenThrow(NotFoundException.class);
		
		mockMvc.perform(get("/recipe/1/show"))
		.andExpect(status().isNotFound())
		.andExpect(view().name("404Error"));
	}
	
	@Test
	void testGetRecipeNumberFormatExcep() throws Exception {
		mockMvc.perform(get("/recipe/ether/show"))
		.andExpect(status().isBadRequest())
		.andExpect(view().name("400Error"));
	}
	
}
