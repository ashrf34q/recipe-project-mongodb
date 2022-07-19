package com.springframework.recipeproject.controllers;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;

import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.services.RecipeService;

public class IndexControllerTest {
	
	@Mock
	RecipeService recipeService;
	
	@Mock
	Model model;
	
	IndexController controller;
	
	@Before
	public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		
		controller = new IndexController(recipeService);		
	}


	@Test
	public void testGetIndexPage() {
		
		Set<Recipe> recipes = new HashSet<>();
		recipes.add(new Recipe());
		
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		recipes.add(recipe);
		
		when(recipeService.getRecipes()).thenReturn(recipes);
		
		@SuppressWarnings("unchecked")
		ArgumentCaptor<Set<Recipe>> argumentCaptor = ArgumentCaptor.forClass(Set.class);
		
		String view = controller.getIndexPage(model);
		
		assertEquals("index", view);
		verify(model, times(1)).addAttribute(eq("recipes"), argumentCaptor.capture());	
		verify(recipeService, times(1)).getRecipes();
		Set<Recipe> controllerSet = argumentCaptor.getValue();
		assertEquals(2, controllerSet.size());
	}

}
