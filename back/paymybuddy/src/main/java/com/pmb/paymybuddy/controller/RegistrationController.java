package com.pmb.paymybuddy.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.dto.UserRegistrationDto;
import com.pmb.paymybuddy.exception.SendEmailException;
import com.pmb.paymybuddy.exception.TooLateEmailValidationException;
import com.pmb.paymybuddy.exception.UnverifiedUserNotFoundException;
import com.pmb.paymybuddy.exception.UserAlreadyExistException;
import com.pmb.paymybuddy.service.RegistrationService;
import com.pmb.paymybuddy.service.UserValidationService;

@RestController
public class RegistrationController {
	
	@Autowired 
	private RegistrationService registrationService;
	
	@Autowired
	private UserValidationService userValidationService;
	
	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody UserRegistrationDto userRegistrationDto)
			throws UnsupportedEncodingException, SendEmailException, UserAlreadyExistException {
		registrationService.startRegistation(userRegistrationDto);
		return new ResponseEntity<>("Un email de validation vous a été envoyé.", HttpStatus.ACCEPTED);
	}

	@GetMapping("/validateUser")
	public ResponseEntity<String> validateUser(@RequestParam String validationToken)
			throws TooLateEmailValidationException, UnverifiedUserNotFoundException {
		userValidationService.validateUser(validationToken);
		return new ResponseEntity<>("Le user a bien été validé. Continuez vers <a href=\"http://localhost:4200\">PayMyBuddy</a>.", HttpStatus.CREATED);
	}
}
