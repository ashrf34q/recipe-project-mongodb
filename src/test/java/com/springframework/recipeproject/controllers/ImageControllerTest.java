package com.springframework.recipeproject.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
import org.springframework.mock.web.MockHttpServletResponse;
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
        command.setId("1");

        when(recipeService.findCommandById(anyString())).thenReturn(command);

        //when
        mockMvc.perform(get("/recipe/1/image"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).findCommandById(anyString());

    }

    @Test
    public void handleImagePost() throws Exception {
        MockMultipartFile multiPartFile =
                new MockMultipartFile("imagefile", "testing.txt", "text/plain",
                        "Spring Framework Guru".getBytes());

        mockMvc.perform(multipart("/recipe/1/image").file(multiPartFile))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string("Location", "/recipe/1/show"))
                .andExpect(view().name("redirect:/recipe/1/show"));

        verify(imageService, times(1)).saveImageFile(anyString(), any());
    }
    
    
    @Test
    public void renderImageFromDB() throws Exception {
    	RecipeCommand command = new RecipeCommand();
    	command.setId("1");
    	
    	String s = "fake image";
    	Byte[] bytes = new Byte[s.getBytes().length];
    	
    	int i = 0;
    	for(byte b : s.getBytes()) {
    		bytes[i++] = b;
    	}
    	
    	command.setImage(bytes);
    	
    	when(recipeService.findCommandById(anyString())).thenReturn(command);
    	
    	MockHttpServletResponse response = mockMvc.perform(get("/recipe/1/recipeImage"))
    			.andExpect(status().isOk())
    			.andReturn().getResponse();    	
    	
    	byte[] responseBytes = response.getContentAsByteArray();
    	
    	assertEquals(s.getBytes().length, responseBytes.length);
    	
    } //end rendereImage test

}
