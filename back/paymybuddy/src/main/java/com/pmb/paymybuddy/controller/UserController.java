package com.pmb.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.DtoService;
import com.pmb.paymybuddy.service.UserService;

@RestController
public class UserController {

	 @Autowired
	 AuthenticationService authenticationService;
	 
	 @Autowired
	 DtoService dtoService;
	
	 @Autowired
	 UserService userService;
	 
	@GetMapping("/user")
	public ResponseEntity<UserDto> user() throws UserNotFoundException {
		User loggedUser = authenticationService.getLoggedUser();
		return new ResponseEntity<>(dtoService.buildUserDto(loggedUser), HttpStatus.OK);
	}
	
	@PutMapping("/user")
	public ResponseEntity<String> changerUserName(@RequestBody UserDto newUserInformations) throws UserNotFoundException {
		User loggedUser = authenticationService.getLoggedUser();	
		userService.changeUserName(loggedUser, newUserInformations.getName());
		return new ResponseEntity<>("", HttpStatus.OK);
	}
}
