package com.generation.Drugstore.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.generation.Drugstore.Repository.UserRepository;
import com.generation.Drugstore.model.UserModel;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserRepository repository;
	
	
	//Função que busca o usuario no banco e retorna se ele existe ou não.
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<UserModel> user = repository.findByEmail(username);
			user.orElseThrow(() -> new UsernameNotFoundException(username + " not found."));
		
		return user.map(UserDetailsImpl::new).get();
	}

	
	
}
