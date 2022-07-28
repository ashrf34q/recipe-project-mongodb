package com.springframework.recipeproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.converters.IngredientToIngredientCmd;
import com.springframework.recipeproject.converters.UoMToUoMCmd;
import com.springframework.recipeproject.domain.Ingredients;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.repositories.RecipeRepository;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCmd ingrToIngrCmd;
	
	@Mock
	RecipeRepository recipeRepository;
	
	IngredientService ingredientService;
	
	 
	
	public IngredientServiceImplTest() {
		this.ingrToIngrCmd = new IngredientToIngredientCmd(new UoMToUoMCmd());
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);
		
		ingredientService = new IngredientServiceImpl(recipeRepository, ingrToIngrCmd);
	}

	@Test
	public void findByIngIdAndRecipeIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId(1L);
		
		Ingredients ingredient = new Ingredients();
		ingredient.setId(1L);
		Ingredients ingredient2 = new Ingredients();
		ingredient2.setId(2L);
		Ingredients ingredient3 = new Ingredients();
		ingredient3.setId(3L);
		
		recipe.addIngredient(ingredient);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyLong())).thenReturn(recipeOptional);
		
		IngredientsCommand ingredientsCommand = ingredientService.findByIngIdAndRecipeId(1L, 3L);
		
		assertEquals(Long.valueOf(3L), ingredientsCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientsCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyLong());
	}
}
