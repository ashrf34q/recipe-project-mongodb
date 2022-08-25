package com.springframework.recipeproject.domain;

import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Recipe {
	
	private String id;
	
	private String description;
	private int prepTime;
	private int cookTime;
	private int servings;
	private String source;
	private String url;
	private String directions;
	private Set<Ingredients> ingredients = new HashSet<>();
	
	private Byte[] image;
	private Difficulty difficulty;
	private Notes notes;
	private Set<Category> categories = new HashSet<>();
	

	public void setNotes(Notes notes) {
		 if (notes != null) {
	            this.notes = notes;
	            notes.setRecipe(this);
	        }
	}
	
	public Recipe addIngredient(Ingredients ingredient) {
		ingredient.setRecipe(this);
		this.ingredients.add(ingredient);
		return this;
	}

}
