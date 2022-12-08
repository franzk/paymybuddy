package com.pmb.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

	@InjectMocks
	private AuthenticationService serviceUnderTest;

	@Mock
	private UserService userService;

	@Test
	void getLoggedUserTest() throws UserNotFoundException {

		// Arrange
		SecurityContextHolder.setContext(mockContext());

		User testUser = new User();
		String testName = RandomString.make(64);
		String testEmail = RandomString.make(64);
		testUser.setName(testName);
		testUser.setEmail(testEmail);

		when(userService.getUserByEmail(any())).thenReturn(Optional.of(testUser));

		// Act
		User result = serviceUnderTest.getLoggedUser();

		// Arrange
		assertThat(result).isEqualTo(testUser);

	}

	@Test
	void getLoggedUserFailTest() throws UserNotFoundException {
		
		// Arrange
		SecurityContextHolder.setContext(mockContext());
				
		when(userService.getUserByEmail(any())).thenReturn(Optional.empty());

		// Act + Assert
		assertThrows(UserNotFoundException.class, () -> serviceUnderTest.getLoggedUser());
	}
	
	SecurityContext mockContext() {
		UsernamePasswordAuthenticationToken authentication = mock(UsernamePasswordAuthenticationToken.class);
		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		return securityContext;
	}
}
