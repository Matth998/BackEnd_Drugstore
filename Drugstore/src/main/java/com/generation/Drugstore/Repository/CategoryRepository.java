package com.generation.Drugstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.Drugstore.model.CategoryModel;

public interface CategoryRepository extends JpaRepository<CategoryModel, Long>{

	public List<CategoryModel> findAllByDescriptionContainingIgnoreCase(String description);
	
}
