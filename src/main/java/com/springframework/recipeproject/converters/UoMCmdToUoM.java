package com.springframework.recipeproject.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import com.springframework.recipeproject.commands.UoMCommand;
import com.springframework.recipeproject.domain.UnitOfMeasure;

import lombok.Synchronized;

@Component
public class UoMCmdToUoM implements Converter<UoMCommand, UnitOfMeasure> {

	@Synchronized
	@Nullable
	@Override
	public UnitOfMeasure convert(UoMCommand source) {
		if (source == null) {
            return null;
        }

        final UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(source.getId());
        uom.setDescription(source.getDescription());
        return uom;
	}

}
