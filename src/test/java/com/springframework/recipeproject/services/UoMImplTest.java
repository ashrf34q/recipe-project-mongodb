package com.springframework.recipeproject.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.springframework.recipeproject.commands.UoMCommand;
import com.springframework.recipeproject.converters.UoMToUoMCmd;
import com.springframework.recipeproject.domain.UnitOfMeasure;
import com.springframework.recipeproject.repositories.UnitOfMeasureRepository;

class UoMImplTest {

	@Mock
	UnitOfMeasureRepository unitOfMeasureRepo;
	
	UoMService service;

	UoMToUoMCmd uoMToUoMCmd = new UoMToUoMCmd();
	
	@BeforeEach
	void setUp() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		service = new UoMServiceImpl(unitOfMeasureRepo, uoMToUoMCmd);
	}

	@Test
	void listUomTest() throws Exception {
		Set<UnitOfMeasure> uoms = new HashSet<>();
		
		UnitOfMeasure uom1 = new UnitOfMeasure();
		uom1.setId(1L);
		UnitOfMeasure uom2 = new UnitOfMeasure();
		uom2.setId(2L);
		//add to the set
		uoms.add(uom1);
		uoms.add(uom2);
		
		when(unitOfMeasureRepo.findAll()).thenReturn(uoms);
		
		Set<UoMCommand> commands = service.listAllUoms();
		
		assertEquals(2, commands.size());
		verify(unitOfMeasureRepo, times(1)).findAll();
		
	}

}
