package com.springframework.recipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.recipeproject.commands.UoMCommand;
import com.springframework.recipeproject.domain.UnitOfMeasure;

import lombok.Synchronized;

@Component
public class UoMToUoMCmd implements Converter<UnitOfMeasure, UoMCommand> {

    @Synchronized
    @Nullable
    @Override
    public UoMCommand convert(UnitOfMeasure unitOfMeasure) {

        if (unitOfMeasure != null) {
            final UoMCommand uomc = new UoMCommand();
            uomc.setId(unitOfMeasure.getId());
            uomc.setDescription(unitOfMeasure.getDescription());
            return uomc;
        }
        return null;
    }

}
