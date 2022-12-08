package com.pmb.paymybuddy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

	@InjectMocks
	LoginController controllerUnderTest;

	@Test
	void loginTest() {

		// Arrange
		Principal mockPrincipal = mock(Principal.class);
		when(mockPrincipal.getName()).thenReturn("me");

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getUserPrincipal()).thenReturn(mockPrincipal);

		// Act
		String result = controllerUnderTest.login(request);

		// Assert
		assertThat(result).isEqualTo("me");

	}

}
