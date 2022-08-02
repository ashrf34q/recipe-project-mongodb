package com.springframework.recipeproject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.services.IngredientService;
import com.springframework.recipeproject.services.RecipeService;
import com.springframework.recipeproject.services.UoMService;

public class IngredientControllerTest {
	
	@Mock
	IngredientService ingredientService;

	@Mock
	RecipeService recipeService;
	
	@Mock
	UoMService uoMService;
	
	IngredientController controller;
	
	MockMvc mockMvc;
	
	RecipeCommand recipeCommand;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		recipeCommand = new RecipeCommand();
		controller = new IngredientController(recipeService, ingredientService, uoMService);
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
		
	} //end testList
	
	
	@Test
	public void testShowIngredient() throws Exception {
		IngredientsCommand ingredientsCommand = new IngredientsCommand();
		
		when(ingredientService.findByIngIdAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientsCommand);
		
		mockMvc.perform(get("/recipe/1/ingredient/2/show"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/show"))
		.andExpect(model().attributeExists("ingredient"));
		
		verify(ingredientService, times(1)).findByIngIdAndRecipeId(anyLong(), anyLong());
	} //end testShow
	
	
	@Test
	public void testUpdateIngredient() throws Exception {
		IngredientsCommand ingredientsCommand = new IngredientsCommand();
		
		when(ingredientService.findByIngIdAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientsCommand);
		when(uoMService.listAllUoms()).thenReturn(new HashSet<>());
		
		mockMvc.perform(get("/recipe/1/ingredient/2/update"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientForm"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"));
		
	} //end testUpdate
	
	
	@Test
	public void testSaveOrUpdate() throws Exception { 	//This is after the user has updated the ingredient and wants to save it
		IngredientsCommand command = new IngredientsCommand();
		command.setId(3L);
		command.setRecipeId(2L);
		
		when(ingredientService.saveIngredientCommand(any())).thenReturn(command);
		
		mockMvc.perform(post("/recipe/2/ingredient")
		.contentType(MediaType.APPLICATION_FORM_URLENCODED)
		.param("id", "")
		.param("description", "some string")
	)
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/2/ingredient/3/show"));
		
	} //end testSave
	
	
	@Test
	public void testNewIngredient() throws Exception {
		//given
		recipeCommand.setId(1L);
		
		//when
		when(recipeService.findCommandById(anyLong())).thenReturn(recipeCommand);
		when(uoMService.listAllUoms()).thenReturn(new HashSet<>());
		
		//then
		mockMvc.perform(get("/recipe/1/ingredient/new"))
		.andExpect(status().isOk())
		.andExpect(view().name("recipe/ingredient/ingredientForm"))
		.andExpect(model().attributeExists("ingredient"))
		.andExpect(model().attributeExists("uomList"));
		
		verify(recipeService, times(1)).findCommandById(anyLong());
		
	}//end testNewIngredient

	
	@Test
	public void testDeleteIngredient() throws Exception {
		
		mockMvc.perform(get("/recipe/1/ingredient/4/delete"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/recipe/1/ingredients"));
		
		verify(ingredientService, times(1)).deleteIngrById(anyLong(), anyLong());
	}
}
