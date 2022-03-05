package com.generation.Drugstore.service;

import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.generation.Drugstore.Repository.UserRepository;
import com.generation.Drugstore.model.UserLogin;
import com.generation.Drugstore.model.UserModel;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	//Cadastro do usuario
	public Optional<UserModel> registerUser(UserModel user){

		if (userRepository.findByEmail(user.getEmail()).isPresent())
			return Optional.empty(); // --> Verifica se no repositorio contém o email digitado, caso existe
		 // ele retorna vazio.
		

		user.setPassword(encryptPassword(user.getPassword())); // Se não existe ele faz a encriptografia da senha

		return Optional.of(userRepository.save(user)); // e por fim salva no repositorio de usuario.

	}

	//Atualização do usuario
	public Optional<UserModel> updateUser(UserModel user) {


		if(userRepository.findById(user.getId()).isPresent()) { // --> Se o usuario existe no banco de dados, ele entra no if.

			Optional<UserModel> buscaUsuario = userRepository.findByEmail(user.getEmail()); // --> Busca o usuario pelo email.

			if ( (buscaUsuario.isPresent()) && ( buscaUsuario.get().getId() != user.getId())) // --> Se o email existir e o Id existir, ele entra no if.
				throw new ResponseStatusException(
						HttpStatus.BAD_REQUEST, "Usuário já existe!", null); // --> Diz que o usuario existe.

			user.setPassword(encryptPassword(user.getPassword())); // --> Pega a senha

			return Optional.ofNullable(userRepository.save(user)); // Atualiza a senha do usuario pela nova digitada.

		}

			return Optional.empty(); // --> Caso algum dos ifs seja falso, ele retorna vazio.

	}

	//Função que faz a autentificação do usuario.
	public Optional<UserLogin> authenticateUser(Optional<UserLogin> userLogin){

		Optional<UserModel> user = userRepository.findByEmail(userLogin.get().getEmail()); // --> Ele pega o usuario do banco de dados

		if(user.isPresent()) { // --> Se o usuario estiver presente (no caso, existir), ele entra no if, caso contrario, retorna vazio.

			if(comparePassword(userLogin.get().getPassword(), user.get().getPassword())) { // --> Aqui ele compara a senha digitada com a senha no banco de dados
																						   // --> Se forem iguais, ele permite o acesso, caso contrario, retorna vazio.
				userLogin.get().setId(user.get().getId()); // Pega o Id do usuario
				userLogin.get().setName(user.get().getName()); // Pega o nome do usuario
				userLogin.get().setPhoto(user.get().getPhoto()); // Pega a foto do usuario
				userLogin.get().setToken(generateBasicToken(user.get().getEmail(), userLogin.get().getPassword())); // Gera o token de acesso do usuario.
				userLogin.get().setPassword(user.get().getPassword()); //Pega a senha do usuario (Criptografada)

				return userLogin;
			}
		}

		return Optional.empty(); //Caso seja falso em algum dos ifs, ele retorna vazio.
	}

	//Função em que está criptografando a senha do usuario.
	private String encryptPassword(String password) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(); // cria uma criptografia de senha.

		return encoder.encode(password); // --> Criptografa a senha.
	}

	//Função responsável por comparar a senha digitada pelo usuario e a senha que contém no banco de dados.
	private boolean comparePassword(String typedPassword, String dbPassword) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		return encoder.matches(typedPassword, dbPassword); // --> Faz a comparação das senhas.

	}

	//Função responsável por gerar o token para o usuario ter acesso a aplicação.
	private String generateBasicToken(String user, String password) {

		String token = user + ":" + password;
		byte[] tokenBase64 = Base64.encodeBase64(token.getBytes(Charset.forName("US-ASCII"))); // --> Um array de byte que pega nosso email + senha e faz um token com base no 
																							   //"Código Padrão Americano para o Intercâmbio de Informação"
		
		return "Basic " + new String(tokenBase64); // retorna nosso token, transformando nosso array de byte em uma nova String.

	}

}
