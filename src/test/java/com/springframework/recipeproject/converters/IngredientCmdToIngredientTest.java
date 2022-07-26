package com.springframework.recipeproject.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.springframework.recipeproject.commands.IngredientsCommand;
import com.springframework.recipeproject.commands.UoMCommand;
import com.springframework.recipeproject.domain.Ingredients;
import com.springframework.recipeproject.domain.Recipe;



public class IngredientCmdToIngredientTest {

    public static final Recipe RECIPE = new Recipe();
    public static final BigDecimal AMOUNT = new BigDecimal("1");
    public static final String DESCRIPTION = "Cheeseburger";
    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final Long UOM_ID = Long.valueOf(2L);

    IngredientCmdToIngredient converter;

    @BeforeEach
    public void setUp() throws Exception {
        converter = new IngredientCmdToIngredient(new UoMCmdToUoM());
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(converter.convert(new IngredientsCommand()));
    }

    @Test
    public void convert() throws Exception {
        //given
        IngredientsCommand command = new IngredientsCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UoMCommand unitOfMeasureCommand = new UoMCommand();
        unitOfMeasureCommand.setId(UOM_ID);
        command.setUnitOfMeasure(unitOfMeasureCommand);

        //when
        Ingredients ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNotNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());
        assertEquals(UOM_ID, ingredient.getUom().getId());
    }

    @Test
    public void convertWithNullUOM() throws Exception {
        //given
        IngredientsCommand command = new IngredientsCommand();
        command.setId(ID_VALUE);
        command.setAmount(AMOUNT);
        command.setDescription(DESCRIPTION);
        UoMCommand unitOfMeasureCommand = new UoMCommand();


        //when
        Ingredients ingredient = converter.convert(command);

        //then
        assertNotNull(ingredient);
        assertNull(ingredient.getUom());
        assertEquals(ID_VALUE, ingredient.getId());
        assertEquals(AMOUNT, ingredient.getAmount());
        assertEquals(DESCRIPTION, ingredient.getDescription());

    }

}