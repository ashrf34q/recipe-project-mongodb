package com.springframework.recipeproject.commands;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import com.springframework.recipeproject.domain.Difficulty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeCommand {
	
	private String id;
	
	@NotBlank
	@Size(min = 3, max = 255)
	private String description;
	
	@Min(1)
	@Max(999)
	private int prepTime;
	
	@Min(1)
	@Max(999)
	private int cookTime;
	
	@Min(1)
	@Max(999)
	private int servings;
	private String source;
	
	@URL
	private String url;
	
	@NotBlank
	private String directions; 
	private Set<IngredientsCommand> ingredients = new HashSet<>();
	private Difficulty difficulty;
	private NotesCommand notes;
	private Set<CategoryCommand> categories = new HashSet<>();
	private Byte[] image;
	
}
