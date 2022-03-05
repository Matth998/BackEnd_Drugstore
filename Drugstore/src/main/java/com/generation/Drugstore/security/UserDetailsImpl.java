package com.generation.Drugstore.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.Drugstore.model.UserModel;

public class UserDetailsImpl implements UserDetails{
	
	private static final long serialVersionUID = 1L; // --> Identificador de versão.
	
	private String userName;
	private String password;
	private List<GrantedAuthority> authorities; // --> Instanciamento da lista que vai ler as autorizações
	
	//Construtor do UserDetailsImpl
	public UserDetailsImpl(UserModel user) {
		
		this.userName = user.getName();
		this.password = user.getPassword();
		
	}
	
	//Contrutor vazio do UserDetailsImpl
	public UserDetailsImpl() {
		
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities; 
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password; 
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

}
