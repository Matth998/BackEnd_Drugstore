package com.generation.Drugstore.Controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.generation.Drugstore.Repository.UserRepository;
import com.generation.Drugstore.model.UserLogin;
import com.generation.Drugstore.model.UserModel;
import com.generation.Drugstore.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService; // --> Pegando UserService.

	@Autowired
	private UserRepository userRepository; // --> Pegando o repositorio do usuario

	//Buscar todos usuarios.
	@GetMapping("/all")
	public ResponseEntity <List<UserModel>> getAll(){
		
		return ResponseEntity.ok(userRepository.findAll());
	}

	//Busca usuario por ID.
	@GetMapping("/{id}")
	public ResponseEntity<UserModel> getById(@PathVariable Long id){
		
		return userRepository.findById(id)
				.map(resp -> ResponseEntity.ok(resp))
				.orElse(ResponseEntity.notFound().build());
		
	}
	
	@GetMapping("/name/{name}")
	public ResponseEntity<List<UserModel>> getByName(@PathVariable String name){
		
		return ResponseEntity.ok(userRepository.findByNameContainingIgnoreCase(name));
		
	}

	//Função de login.
	@PostMapping("/login")
	public ResponseEntity<UserLogin> login(@RequestBody Optional<UserLogin> userLogin){

		return userService.authenticateUser(userLogin) // --> chama afunção de autentificação, para verificar se o usuario é valido.
			.map(resp -> ResponseEntity.status(HttpStatus.OK).body(resp)) // --> se for valido, ele da um status 200
			.orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()); // --> se nao for valido, ele da um 401(sem autorização)
		
	}

	//Função para cadastrar um novo usuario.
	@PostMapping("/register")
	public ResponseEntity<UserModel> postUsuario(@Valid @RequestBody UserModel user){

		return userService.registerUser(user) //Chama a função de cadastrar usuario.
			.map(resp -> ResponseEntity.status(HttpStatus.CREATED).body(resp)) // --> Se tudo ocorrer certo com todos os campos obrigatorios digitados, ele da um CREATED.
			.orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build()); // --> Se algo estiver errado, ele da um Bad Request (400)
		
	}

	//Função de atualização do usuario
	@PutMapping("/update")
	public ResponseEntity<UserModel> putUsuario(@Valid @RequestBody UserModel user){

		return userService.updateUser(user) // --> Chamando a função de atualização do usuario
			.map(resposta -> ResponseEntity.status(HttpStatus.OK).body(resposta)) // --> Caso o usuario esteja no banco, e a alteração seja aceita, ele da um status 200.
			.orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build()); // --> Caso o usuario não esteja no banco de dados, ele da um Not Found (404)
		
	}

}
