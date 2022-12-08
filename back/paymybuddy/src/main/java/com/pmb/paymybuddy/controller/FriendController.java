package com.pmb.paymybuddy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.exception.AlreadyFriendException;
import com.pmb.paymybuddy.exception.FriendNotFoundException;
import com.pmb.paymybuddy.exception.SelfFriendshipException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.UserService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class FriendController {

	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationService authenticationService;

	@GetMapping("/friends")
	public ResponseEntity<List<UserDto>> getFriends() throws UserNotFoundException {
		return new ResponseEntity<>(userService.getDtoFriends(authenticationService.getLoggedUser()), HttpStatus.OK);
	}
	
	@PostMapping("/friend")
	public ResponseEntity<User> addFriend(@RequestBody UserDto friendDto) throws UserNotFoundException, SelfFriendshipException, AlreadyFriendException {
		return new ResponseEntity<>(userService.addFriend(authenticationService.getLoggedUser(), friendDto.getEmail()), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/friend")
	public ResponseEntity<User> removeFriend(@RequestParam String email) throws UserNotFoundException, FriendNotFoundException {
		log.info(email);		
		return new ResponseEntity<>(userService.removeFriend(authenticationService.getLoggedUser(), email), HttpStatus.OK);
	}
	
}
