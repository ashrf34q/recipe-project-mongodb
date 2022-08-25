package com.springframework.recipeproject.controllers;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.services.RecipeService;

public class IndexControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController controller;
	
	@BeforeEach
	public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		
		controller = new IndexController(recipeService);		
	}


	@Test
	public void testMockMvc() throws Exception {
		MockMvc mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		
		mockMvc.perform(get("/"))
		.andExpect(status().isOk())
		.andExpect(view().name("index"));
	}
	
	
	@Test
	public void testGetIndexPage() throws Exception {
		
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		
		Recipe recipe = new Recipe();
		recipe.setId("1");
		recipes.add(recipe);
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		//when
		String view = controller.getIndexPage(model);
		
		//then
		assertEquals("index", view);
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());	
		verify(recipeService, times(1)).getRecipes();
		Set<Recipe> controllerSet = argumentCaptor.getValue();
		assertEquals(2, controllerSet.size());
	}

}
