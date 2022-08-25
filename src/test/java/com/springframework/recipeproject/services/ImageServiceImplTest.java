package com.springframework.recipeproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.springframework.recipeproject.domain.Recipe;
import com.springframework.recipeproject.repositories.RecipeRepository;

class ImageServiceImplTest {
	
	@Mock
	RecipeRepository recipeRepository;
	
	ImageService imageService;

	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		imageService = new ImageServiceImpl(recipeRepository);
	}

	@Test
	void testSaveImageFile() throws Exception {
		String id = "2";
		MultipartFile multipartFile = new MockMultipartFile("imagefile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());
		
		Recipe recipe = new Recipe();
		recipe.setId(id);
		Optional<Recipe> recipeOpt = Optional.of(recipe);
		
		when(recipeRepository.findById(anyString())).thenReturn(recipeOpt);
		
		ArgumentCaptor<Recipe> argumentCaptor = ArgumentCaptor.forClass(Recipe.class);
		
		//when
		imageService.saveImageFile(id, multipartFile);
		
		//then
		verify(recipeRepository, times(1)).findById(anyString());
		verify(recipeRepository).save(argumentCaptor.capture());
		Recipe savedRecipe = argumentCaptor.getValue();
		assertEquals(multipartFile.getBytes().length, savedRecipe.getImage().length);
		
	}

}
