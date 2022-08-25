package com.springframework.recipeproject.domain;


import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CategoryTest {
	
	  Category category;

	@BeforeEach
	public  void setUp() {
			category = new Category();
	}
	
	@Test
	public void getId() throws Exception {
		String idValue = "4";
		
		category.setId(idValue);
		
		assertEquals(idValue, category.getId());
	}
	
	@Test
	public void getdescription() throws Exception {
		
	}
	
	@Test
	public void getRecipes() throws Exception {
		
	}

}
