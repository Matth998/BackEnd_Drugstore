package com.generation.Drugstore.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.Drugstore.Repository.CategoryRepository;
import com.generation.Drugstore.model.CategoryModel;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CategoryController {

	@Autowired
	private CategoryRepository repository;
	
	@GetMapping
	public ResponseEntity<List<CategoryModel>> getAll(){
		
		return ResponseEntity.ok(repository.findAll());
		
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<CategoryModel> getById(@PathVariable long id){
		
		return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/category/{category}")
	public ResponseEntity<List<CategoryModel>> getByCategory(@PathVariable String name){
		
		return ResponseEntity.ok(repository.findAllByDescriptionContainingIgnoreCase(name));
		
	}
	
	@PostMapping
	public ResponseEntity<CategoryModel> post(@RequestBody CategoryModel category){
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(repository.save(category));
	}
	
	@PutMapping
	public ResponseEntity<CategoryModel> put(@RequestBody CategoryModel category){
		
		return ResponseEntity.ok(repository.save(category));
	}
	
	@DeleteMapping("/{id}")
	public void delete(@PathVariable long id) {
		
		repository.deleteById(id);
	}
	
}
