package com.generation.Drugstore.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "tb_user")
public class UserModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@NotBlank(message = "The name field cannot be null")
	@Size(min = 3, message = "The name field must be at least 3 characters long.")
	private String name;
	
	@NotBlank(message = "The email field cannot be null")
	@Email(message = "Invalid email")
	private String email;
	
	@NotBlank
	@Size(min = 8, message = "The password must be have least 8 characters long")
	private String password;
	
	private String photo;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	@JsonIgnoreProperties("user")
	private List<ProductModel> product;
	
	public UserModel(Long id, String name, String email, String password, String photo) {
		
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.photo = photo;
		
	}
	
	public UserModel() {
		
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public List<ProductModel> getProduct() {
		return product;
	}

	public void setProduct(List<ProductModel> product) {
		this.product = product;
	}
	
}
