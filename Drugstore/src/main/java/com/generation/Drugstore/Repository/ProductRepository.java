package com.generation.Drugstore.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generation.Drugstore.model.ProductModel;

public interface ProductRepository extends JpaRepository<ProductModel, Long>{

	public List<ProductModel> findAllByNameContainingIgnoreCase(String name);
	
}
