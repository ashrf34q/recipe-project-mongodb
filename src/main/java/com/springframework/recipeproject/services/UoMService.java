package com.springframework.recipeproject.services;

import java.util.Set;

import com.springframework.recipeproject.commands.UoMCommand;

public interface UoMService {

	Set<UoMCommand> listAllUoms();

}
