package com.springframework.recipeproject.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springframework.recipeproject.domain.Category;
import com.springframework.recipeproject.domain.UnitOfMeasure;
import com.springframework.recipeproject.repositories.CategoryRepository;
import com.springframework.recipeproject.repositories.UnitOfMeasureRepository;

@Controller
public class IndexController {
	
	private CategoryRepository categoryRepository;
	private UnitOfMeasureRepository unitOfMeasureRepository;
	
	public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
		this.categoryRepository = categoryRepository;
		this.unitOfMeasureRepository = unitOfMeasureRepository;
	}

	@RequestMapping({"", "/", "/index"})
	public String getIndexPage() {

		Optional<Category> categoryOptional = categoryRepository.findByDescription("Mexican");
		Optional<Category> categoryOptional2 = categoryRepository.findByDescription("Mediterranean");

		Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Cup");
		
		System.out.println("Category 1 Id is: " + categoryOptional.get().getId());
		System.out.println("Category 2 Id is: " + categoryOptional2.get().getId());
		System.out.println("UOM Id is: " + unitOfMeasureOptional.get().getId());
		
		return "index";
	}
	
}
