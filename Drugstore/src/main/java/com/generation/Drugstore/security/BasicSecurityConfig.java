package com.generation.Drugstore.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@EnableWebSecurity
public class BasicSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		
		auth.userDetailsService(userDetailsService);
		
		//Criação do usuario em memoria.
		auth.inMemoryAuthentication()
			.withUser("admin") // --> Define o nome do usuario
			.password(passwordEncoder().encode("root")) // --> Define a senha do usuario
			.authorities("ROLE_USER"); // --> Define o nivel de autorização do usuario
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(); // --> Ele é responsavel por encriptografar a senha digitada.
		
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.authorizeRequests() // --> Sessão para autorização do usuario
			.antMatchers("/user/login").permitAll() // --> Permite com que todos tenham acesso ao login
			.antMatchers("/user/register").permitAll() // --> Permite com que todo tenham acesso ao cadastro
			.antMatchers(HttpMethod.OPTIONS).permitAll() // --> Para indicar quais opções tem disponivel.
			//Camada em que envia uma cadeia de caracter em base64 que contem o user e senha.
			.anyRequest().authenticated()
			.and().httpBasic() // --> 
			.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // --> é um filtro de segurança.
			.and().cors() // --> Ele é quem informa ao navegador para permitir a execução do dominio, com algumas permissões que foram selecionadas.
			.and().csrf().disable(); // --> Desabilitando o CSRF pois não estamos fazendo teste por navegador (por enquanto).
		
	}
	
}
