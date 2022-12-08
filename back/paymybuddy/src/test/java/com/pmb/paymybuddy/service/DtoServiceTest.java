package com.pmb.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.model.User;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class DtoServiceTest {

	@InjectMocks
	DtoService serviceUnderTest;
	
	@Test
	void buildUserDtoTest() {
		
		// Arrange
		User testUser = new User();
		String testName = RandomString.make(64);
		String testEmail = RandomString.make(64);
		testUser.setName(testName);
		testUser.setEmail(testEmail);
		
		// Act
		UserDto result = serviceUnderTest.buildUserDto(testUser);
		
		// Assert
		assertThat(result.getName()).isEqualTo(testName);
		assertThat(result.getEmail()).isEqualTo(testEmail);
		
	}
}
