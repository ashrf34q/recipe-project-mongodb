package com.springframework.recipeproject.controllers;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.services.IngredientService;
import com.springframework.recipeproject.services.RecipeService;

public class IngredientControllerTest {
	
	@Mock
	IngredientService ingredientService;

	@Mock
	RecipeService recipeService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	RecipeCommand recipeCommand;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		recipeCommand = new RecipeCommand();
		controller = new IngredientController(recipeService, ingredientService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

	@Test
	public void testListIngredients() throws Exception {
		
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		
		mockMvc.perform(get("/recipe/1/ingredients"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/list"))
		.andExpect(model().attributeExists("recipe"));
		
		verify(recipeService, times(1)).findCommandById(anyLong());
	}
	
	@Test
	public void testShowIngredient() throws Exception {
		IngredientsCommand ingredientsCommand = new IngredientsCommand();
		
		when(ingredientService.findByIngIdAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientsCommand);
		
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/show"))
		.andExpect(model().attributeExists("ingredient"));
		
		verify(ingredientService, times(1)).findByIngIdAndRecipeId(anyLong(), anyLong());
		
	}

}
