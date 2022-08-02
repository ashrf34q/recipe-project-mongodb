package com.springframework.recipeproject.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.springframework.recipeproject.commands.RecipeCommand;
import com.springframework.recipeproject.services.ImageService;
import com.springframework.recipeproject.services.RecipeService;

public class ImageControllerTest {
	
	@Mock
	ImageService imageService;
	
	@Mock
	RecipeService recipeService;
	
	ImageController controller;
	
	MockMvc mockMvc;
	
	@BeforeEach
	public void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		controller = new ImageController(imageService, recipeService);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	public void uploadImageTest() throws Exception {
		//given
		RecipeCommand command = new RecipeCommand();
		command.setId(1L);
		
		when(recipeService.findCommandById(anyLong())).thenReturn(command);
		
		mockMvc.perform(get("/recipe/1/image"))
		.andExpect(status().isOk())
		.andExpect(model().attributeExists("recipe"))
		.andExpect(view().name("recipe/imageUploadForm"));
	}
	
	
	@Test
	public void handleImagePost() throws Exception {
		MockMultipartFile multiPartFile = 
						new MockMultipartFile("imageFile", "testing.txt", "text/plain", "Spring Framework Guru".getBytes());
		
		mockMvc.perform(multipart("/recipe/1/image").file(multiPartFile))
		.andExpect(status().is3xxRedirection())
		.andExpect(header().string("Location", "/recipe/1/show"));
		
		verify(imageService, times(1)).saveImageFile(anyLong(), any());
		
	}

}
