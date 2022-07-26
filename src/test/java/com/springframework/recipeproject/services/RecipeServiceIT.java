package com.springframework.recipeproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.converters.RecipeCmdToRecipe;
import com.springframework.recipeproject.converters.RecipeToRecipeCmd;
import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.repositories.RecipeRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class RecipeServiceIT {
	
	private static String NEW_DESCRIPTION;

	@Autowired
	RecipeService recipeService;
	
	@Autowired
	RecipeRepository recipeRepository;
	
	@Autowired
	RecipeCmdToRecipe recipeCmdToRecipe;
	
	@Autowired
	RecipeToRecipeCmd recipeToRecipeCmd;
	
	
	@BeforeEach
	void setUp() throws Exception {
		NEW_DESCRIPTION = "New Description";
	}

	@Transactional
	@Test
	void saveDescriptionTest() {
		Iterable<Recipe> recipes = recipeRepository.findAll();
		Recipe testRecipe = recipes.iterator().next(); //get the first iterator
		RecipeCommand testRecipeCmd = recipeToRecipeCmd.convert(testRecipe);

		testRecipeCmd.setDescription(NEW_DESCRIPTION);
		RecipeCommand savedRecipeCmd = recipeService.saveRecipeCommand(testRecipeCmd);
		
		//test
		assertEquals(NEW_DESCRIPTION, savedRecipeCmd.getDescription());
		assertEquals(testRecipe.getId(), savedRecipeCmd.getId());
		assertEquals(testRecipe.getCategories().size(), savedRecipeCmd.getCategories().size());
		assertEquals(testRecipe.getIngredients().size(), savedRecipeCmd.getIngredients().size());
		
	}

}
