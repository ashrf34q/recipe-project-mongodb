package com.springframework.recipeproject.services;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.stereotype.Service;

import com.springframework.recipeproject.commands.UoMCommand;
import com.springframework.recipeproject.converters.UoMToUoMCmd;
import com.springframework.recipeproject.repositories.UnitOfMeasureRepository;

@Service
public class UoMServiceImpl implements UoMService {

	private final UnitOfMeasureRepository unitOfMeasureRepo;
	private final UoMToUoMCmd uoMToUoMCmd;
	
	public UoMServiceImpl(UnitOfMeasureRepository unitOfMeasureRepo, UoMToUoMCmd uoMToUoMCmd) {
		this.unitOfMeasureRepo = unitOfMeasureRepo;
		this.uoMToUoMCmd = uoMToUoMCmd;
	}

	@Override
	public Set<UoMCommand> listAllUoms() {
		
		return StreamSupport.stream(unitOfMeasureRepo.findAll()
				.spliterator(), false)
				.map(uoMToUoMCmd::convert)
				.collect(Collectors.toSet());
	}

}
