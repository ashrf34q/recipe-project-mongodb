package com.springframework.recipeproject.commands;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class IngredientsCommand {
	 	private String id;
	 	private String recipeId;
	    private String description;
	    private BigDecimal amount;
	    private UoMCommand uom;
}
