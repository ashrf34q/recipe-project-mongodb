package com.springframework.recipeproject.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Ingredients {

	private String id;
	
	
	private Recipe recipe;
	
	
	private UnitOfMeasure uom;
	
	private String description;
	private BigDecimal amount;
	
	public Ingredients() {  };
	
	public Ingredients(String description, BigDecimal amount, UnitOfMeasure UOM, Recipe recipe) {
			this.description = description;
			this.amount = amount;
			this.recipe = recipe;
			this.uom = UOM;
		}
	
	
	
	
}
