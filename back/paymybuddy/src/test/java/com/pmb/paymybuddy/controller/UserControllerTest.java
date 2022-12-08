package com.pmb.paymybuddy.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.DtoService;
import com.pmb.paymybuddy.service.UserService;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

	@InjectMocks
	UserController controllerUnderTest;

	@Mock
	AuthenticationService authenticationService;

	@Mock
	private DtoService dtoService;
	
	 @Mock
	 UserService userService;

	@Test
	void userTest() throws UserNotFoundException {

		// Arrange
		String testName = RandomString.make(64);
		User testUser = new User();
		testUser.setName(testName);
		when(authenticationService.getLoggedUser()).thenReturn(testUser);
		
		// Act
		controllerUnderTest.user();
		
		// Assert
		verify(dtoService, times(1)).buildUserDto(testUser);
		
	}
	
	@Test
	void changerUserNameTest() throws UserNotFoundException {
		
		// Arrange
		String testName = RandomString.make(64);
		User testUser = new User();
		testUser.setName(testName);
		when(authenticationService.getLoggedUser()).thenReturn(testUser);
		
		UserDto newUserInformations = new UserDto();
		String testNewName = RandomString.make(64);
		newUserInformations.setName(testNewName);
		
		// Act
		controllerUnderTest.changerUserName(newUserInformations);
		
		// Assert
		verify(userService, times(1)).changeUserName(testUser, testNewName);
	}

}
