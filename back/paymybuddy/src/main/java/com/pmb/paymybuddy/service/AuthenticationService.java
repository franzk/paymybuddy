package com.pmb.paymybuddy.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;

@Service
public class AuthenticationService {

	@Autowired
	private UserService userService;

	public User getLoggedUser() throws UserNotFoundException {
		
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
				
		Optional<User> loggedUser = userService.getUserByEmail(email);

		if (loggedUser.isEmpty()) {
			throw new UserNotFoundException();
		} else {
			return loggedUser.get();
		}
	}
}
