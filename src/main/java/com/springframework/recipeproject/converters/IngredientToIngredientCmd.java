package com.springframework.recipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.domain.Ingredients;

import lombok.Synchronized;

@Component
public class IngredientToIngredientCmd implements Converter<Ingredients, IngredientsCommand> {

    private final UoMToUoMCmd uomConverter;

    public IngredientToIngredientCmd(UoMToUoMCmd uomConverter) {
        this.uomConverter = uomConverter;
    }

    @Synchronized
    @Nullable
    @Override
    public IngredientsCommand convert(Ingredients ingredient) {
        if (ingredient == null) {
            return null;
        }

        IngredientsCommand ingredientCommand = new IngredientsCommand();
        ingredientCommand.setId(ingredient.getId());
        ingredientCommand.setAmount(ingredient.getAmount());
        ingredientCommand.setDescription(ingredient.getDescription());
        ingredientCommand.setUnitOfMeasure(uomConverter.convert(ingredient.getUom()));
        return ingredientCommand;
    }

}
