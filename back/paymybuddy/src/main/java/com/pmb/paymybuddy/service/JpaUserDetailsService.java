package com.pmb.paymybuddy.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UserRepository;

@Service
public class JpaUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found : " + username));
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				Collections.singletonList(new SimpleGrantedAuthority("USER")));
	}

}
