package com.springframework.recipeproject.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"recipe"})
@Entity
public class Ingredients {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Recipe recipe;
	
	@OneToOne(fetch = FetchType.EAGER)
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
