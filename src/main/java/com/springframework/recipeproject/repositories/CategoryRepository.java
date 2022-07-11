package com.springframework.recipeproject.repositories;

import org.springframework.data.repository.CrudRepository;

import com.springframework.recipeproject.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

}
