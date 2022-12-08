package com.pmb.paymybuddy.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.UnsupportedEncodingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.dto.UserRegistrationDto;
import com.pmb.paymybuddy.exception.SendEmailException;
import com.pmb.paymybuddy.exception.TooLateEmailValidationException;
import com.pmb.paymybuddy.exception.UnverifiedUserNotFoundException;
import com.pmb.paymybuddy.exception.UserAlreadyExistException;
import com.pmb.paymybuddy.service.RegistrationService;
import com.pmb.paymybuddy.service.UserValidationService;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class RegistrationControllerTest {

	@InjectMocks
	RegistrationController controllerUnderTest;
	
	@Mock
	RegistrationService registrationService;
	
	@Mock
	UserValidationService userValidationService;
	
	@Test
	void registerTest() throws UnsupportedEncodingException, SendEmailException, UserAlreadyExistException {
		
		// Arrange
		UserRegistrationDto testDto = new UserRegistrationDto();
		
		// Act
		controllerUnderTest.register(testDto);
		
		// Assert
		verify(registrationService, times(1)).startRegistation(testDto);
	}
	
	@Test
	void validateUser() throws TooLateEmailValidationException, UnverifiedUserNotFoundException {
		
		// Arrange
		String token = RandomString.make(64);
		
		// Act
		controllerUnderTest.validateUser(token);
		
		// Assert
		verify(userValidationService, times(1)).validateUser(token);
	}
	
}
