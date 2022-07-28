package com.springframework.recipeproject.services;

import java.util.Set;

import com.springframework.recipeproject.commands.UoMCommand;
import com.springframework.recipeproject.converters.UoMToUoMCmd;
import com.springframework.recipeproject.repositories.UnitOfMeasureRepository;

public class UoMServiceImpl implements UoMService {

	public UoMServiceImpl(UnitOfMeasureRepository unitOfMeasureRepo, UoMToUoMCmd uoMToUoMCmd) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Set<UoMCommand> listAllUoms() {
		
		return null;
	}

}
