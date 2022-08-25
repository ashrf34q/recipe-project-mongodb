package com.springframework.recipeproject.services;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.recipeproject.converters.RecipeCmdToRecipe;
import com.springframework.recipeproject.converters.RecipeToRecipeCmd;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.exceptions.NotFoundException;
import com.springframework.recipeproject.repositories.RecipeRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RecipeServiceImplTest {
	
	 RecipeServiceImpl recipeService;
	 
	 @Mock
	 RecipeRepository recipeRepository;
	 
	 @Mock
	 RecipeCmdToRecipe recipeCmdToRecipe;
	 
	 @Mock
	 RecipeToRecipeCmd recipeToRecipeCmd;
	 
	 @BeforeEach
	 public void setUp() throws Exception{
		MockitoAnnotations.openMocks(this);
		
		recipeService = new RecipeServiceImpl(recipeRepository, recipeToRecipeCmd, recipeCmdToRecipe);		
		
	 }

	@Test
	public void testGetRecipes() throws Exception {
		
		Recipe recipe = new Recipe();
		HashSet<Recipe> recipesData = new HashSet<>();
		recipesData.add(recipe);
		
		when(recipeRepository.findAll()).thenReturn(recipesData);
		
		Set<Recipe> recipes = recipeService.getRecipes();
		
		assertEquals(recipes.size(), 1);
		verify(recipeRepository, times(1)).findAll();
	}
	
	@Test
	public void testFindById() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		Recipe returnedRecipe = recipeService.findById(recipe.getId());
		
		assertNotNull(returnedRecipe);
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, never()).findAll();
	}
	
	@Test
	public void testDeleteById() throws Exception {
		
		String id = "2";
		
		recipeService.deleteById(id);
		
		verify(recipeRepository, times(1)).deleteById(anyString());
	}
	
	
	 @Test
	public void getRecipeByIdTestNotFound() {
		 Optional<Recipe> recipeOptional = Optional.empty();

	     when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
	     
	     Exception exception = assertThrows(NotFoundException.class, () -> {
	    	  recipeService.findById("1");
	     });
	     
	     log.debug(exception.getMessage());
	}

}
