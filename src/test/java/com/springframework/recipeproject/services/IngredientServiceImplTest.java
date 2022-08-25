package com.springframework.recipeproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.converters.IngredientCmdToIngredient;
import com.springframework.recipeproject.converters.IngredientToIngredientCmd;
import com.springframework.recipeproject.converters.UoMCmdToUoM;
import com.springframework.recipeproject.converters.UoMToUoMCmd;
import com.springframework.recipeproject.domain.Ingredients;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.repositories.RecipeRepository;
import com.springframework.recipeproject.repositories.UnitOfMeasureRepository;

public class IngredientServiceImplTest {

	private final IngredientToIngredientCmd ingrToIngrCmd;
	private final IngredientCmdToIngredient ingrCmdToIngr;
	
	@Mock
	 UnitOfMeasureRepository uomRepo;
	
	@Mock
	RecipeRepository recipeRepository;
	
	IngredientService ingredientService;
	
	public IngredientServiceImplTest() {
		this.ingrToIngrCmd = new IngredientToIngredientCmd(new UoMToUoMCmd());
		this.ingrCmdToIngr = new IngredientCmdToIngredient(new UoMCmdToUoM());
	}
	
	@BeforeEach
	public void setUp() throws Exception {
		
		MockitoAnnotations.openMocks(this);
		
		ingredientService = new IngredientServiceImpl(recipeRepository, ingrToIngrCmd, uomRepo, ingrCmdToIngr);
	}

	@Test
	public void findByIngIdAndRecipeIdTest() throws Exception {
		Recipe recipe = new Recipe();
		recipe.setId("1");
		
		Ingredients ingredient = new Ingredients();
		ingredient.setId("1");
		Ingredients ingredient2 = new Ingredients();
		ingredient2.setId("2");
		Ingredients ingredient3 = new Ingredients();
		ingredient3.setId("3");
		
		recipe.addIngredient(ingredient);
		recipe.addIngredient(ingredient2);
		recipe.addIngredient(ingredient3);
		Optional<Recipe> recipeOptional = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		
		IngredientsCommand ingredientsCommand = ingredientService.findByIngIdAndRecipeId("1", "3");
		
		assertEquals(Long.valueOf(3L), ingredientsCommand.getId());
		assertEquals(Long.valueOf(1L), ingredientsCommand.getRecipeId());
		verify(recipeRepository, times(1)).findById(anyString());
	}
	
	@Test
	public void saveIngredientCmdTest() throws Exception {
		IngredientsCommand command = new IngredientsCommand();
        command.setId("3");
        command.setRecipeId("2");

        Optional<Recipe> recipeOptional = Optional.of(new Recipe());

        Recipe savedRecipe = new Recipe();
		savedRecipe.addIngredient(new Ingredients());
		savedRecipe.getIngredients().iterator().next().setId("3");
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOptional);
		when(recipeRepository.save(any())).thenReturn(savedRecipe);
		
		IngredientsCommand savedCmd = ingredientService.saveIngredientCommand(command);
		
		assertEquals(command.getId(), savedCmd.getId());
//		assertEquals(command.getRecipeId(), savedCmd.getRecipeId());
		 verify(recipeRepository, times(1)).findById(anyString());
	     verify(recipeRepository, times(1)).save(any(Recipe.class));	
	}
	
	
	@Test
	public void deleteIngrByIdTest() throws Exception {
		Recipe recipe = new Recipe();
		Ingredients ingredient = new Ingredients();
		ingredient.setId("7");
		recipe.addIngredient(ingredient);
		ingredient.setRecipe(recipe);
		Optional<Recipe> recipeOpt = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOpt);
		
		ingredientService.deleteIngrById("2", "7");
		
		//then
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository, times(1)).save(any(Recipe.class));
		
	}
} 
