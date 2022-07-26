package com.springframework.recipeproject.converters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.springframework.recipeproject.commands.CategoryCommand;
import com.springframework.recipeproject.domain.Category;


public class CategoryToCategoryCmdTest {

    public static final Long ID_VALUE = Long.valueOf(1L);
    public static final String DESCRIPTION = "descript";
    CategoryToCategoryCmd convter;

    @BeforeEach
    public void setUp() throws Exception {
        convter = new CategoryToCategoryCmd();
    }

    @Test
    public void testNullObject() throws Exception {
        assertNull(convter.convert(null));
    }

    @Test
    public void testEmptyObject() throws Exception {
        assertNotNull(convter.convert(new Category()));
    }

    @Test
    public void convert() throws Exception {
        //given
        Category category = new Category();
        category.setId(ID_VALUE);
        category.setDescription(DESCRIPTION);

        //when
        CategoryCommand categoryCommand = convter.convert(category);

        //then
        assertEquals(ID_VALUE, categoryCommand.getId());
        assertEquals(DESCRIPTION, categoryCommand.getDescription());

    }

}