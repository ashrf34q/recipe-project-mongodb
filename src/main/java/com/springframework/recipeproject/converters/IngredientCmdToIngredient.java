package com.springframework.recipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.domain.Ingredients;

import lombok.Synchronized;

@Component
public class IngredientCmdToIngredient implements Converter<IngredientsCommand, Ingredients>{
	
	private final UoMCmdToUoM uomConverter;
	
	public IngredientCmdToIngredient(UoMCmdToUoM uomConverter) {
		this.uomConverter = uomConverter;
	}



	@Synchronized  	
	@Nullable
	@Override
	public Ingredients convert(IngredientsCommand source) {
		 if (source == null) {
	            return null;
	        }

	        final Ingredients ingredient = new Ingredients();
	        ingredient.setId(source.getId());
	        ingredient.setAmount(source.getAmount());
	        ingredient.setDescription(source.getDescription());
	        ingredient.setUom(uomConverter.convert(source.getUom()));
	        return ingredient;
	}

	
	

}
