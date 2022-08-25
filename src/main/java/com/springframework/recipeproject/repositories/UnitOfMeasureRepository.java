package com.springframework.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

import com.springframework.recipeproject.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, String> {

	Optional<UnitOfMeasure> findByDescription(String description);
}
