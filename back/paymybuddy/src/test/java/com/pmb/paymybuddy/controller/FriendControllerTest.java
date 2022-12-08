package com.pmb.paymybuddy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.assertj.core.internal.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.exception.AlreadyFriendException;
import com.pmb.paymybuddy.exception.FriendNotFoundException;
import com.pmb.paymybuddy.exception.SelfFriendshipException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.UserService;

@ExtendWith(MockitoExtension.class)
class FriendControllerTest {
	
	@InjectMocks
	FriendController controllerUnderTest;
	
	@Mock
	UserService userService;
	
	@Mock
	AuthenticationService authenticationService;

	@Test
	void getFriendsTest() throws UserNotFoundException {
		
		// Arrange
		User testUser = new User();
		when(authenticationService.getLoggedUser()).thenReturn(testUser);
		
		// Act
		controllerUnderTest.getFriends();
		
		// Assert
		verify(userService, times(1)).getDtoFriends(testUser);
	}
	
	@Test
	void addFriendTest() throws UserNotFoundException, SelfFriendshipException, AlreadyFriendException {
		// Arrange
		UserDto testDto = new UserDto();
		testDto.setEmail(RandomString.make(64));
		
		when(authenticationService.getLoggedUser()).thenReturn(new User());
		
		// Act
		controllerUnderTest.addFriend(testDto);
		
		// Assert
		verify(userService, times(1)).addFriend(any(), any());
		
	}

	@Test
	void removeFriendTest() throws UserNotFoundException, FriendNotFoundException {
		// Arrange
		when(authenticationService.getLoggedUser()).thenReturn(new User());
		
		// Act
		controllerUnderTest.removeFriend(RandomString.make(64));
		
		// Assert
		verify(userService, times(1)).removeFriend(any(), any());
		
	}


}
