package com.springframework.recipeproject.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Ingredients {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Recipe recipe;
	
	private String description;
	private BigDecimal amount;
	
	public Ingredients() {  };
	
	public Ingredients(String description, BigDecimal amount, UnitOfMeasure UOM, Recipe recipe) {
			this.description = description;
			this.amount = amount;
			this.recipe = recipe;
			this.uom = UOM;
		}
	
	
	@OneToOne
	private UnitOfMeasure uom;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Recipe getRecipe() {
		return recipe;
	}
	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public UnitOfMeasure getUom() {
		return uom;
	}
	public void setUom(UnitOfMeasure uom) {
		this.uom = uom;
	}
	
}
