package com.pmb.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pmb.paymybuddy.exception.TooLateEmailValidationException;
import com.pmb.paymybuddy.exception.UnverifiedUserNotFoundException;
import com.pmb.paymybuddy.model.UnverifiedUser;
import com.pmb.paymybuddy.repository.UnverifiedUserRepository;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class UserValidationServiceTest {

	@InjectMocks
	private UserValidationService serviceUnderTest;
	
	@Mock
	private UnverifiedUserRepository unverifiedUserRepository;
	
	@Mock
	private PasswordEncoder passwordEncoder;

	@Mock
	private EmailService emailSendService;
	
	@Mock
	private UserService userService;
	

	
	@Test
	void validateUserTest() throws TooLateEmailValidationException, UnverifiedUserNotFoundException {
		// Arrange
		
		UnverifiedUser testUnverifiedUser = new UnverifiedUser();
		testUnverifiedUser.setName(RandomString.make(64));
		testUnverifiedUser.setEmail(RandomString.make(64));
		testUnverifiedUser.setPassword(RandomString.make(64));
		
		Iterable<UnverifiedUser> testList = List.of(testUnverifiedUser);
		
		when(unverifiedUserRepository.getByValidationTokenOrderByDateTimeDesc(any())).thenReturn(testList);
	
		// Act
		serviceUnderTest.validateUser(RandomString.make(64));

		// Assert
		verify(userService, times(1)).save(any());
	}
	
	@Test
	void validateUserWithUnverifiedUserNotFoundExceptionTest() {
		
		Iterable<UnverifiedUser> testList = new ArrayList<>();
		
		// Arrange
		when(unverifiedUserRepository.getByValidationTokenOrderByDateTimeDesc(any())).thenReturn(testList);
		
		// Act + Assert
		assertThrows(UnverifiedUserNotFoundException.class, () -> serviceUnderTest.validateUser(RandomString.make(64)));
	}
	
	@Test
	void validateUserWithTooLateEmailValidationExceptionTest() {
		// Arrange
		UnverifiedUser testUnverifiedUser = new UnverifiedUser();
		testUnverifiedUser.setName(RandomString.make(64));
		testUnverifiedUser.setEmail(RandomString.make(64));
		testUnverifiedUser.setPassword(RandomString.make(64));
		testUnverifiedUser.setDateTime(LocalDateTime.now().minusDays(100));
		
		Iterable<UnverifiedUser> testList = List.of(testUnverifiedUser);
		
		when(unverifiedUserRepository.getByValidationTokenOrderByDateTimeDesc(any())).thenReturn(testList);
		
		// Act + Assert
		assertThrows(TooLateEmailValidationException.class, () -> serviceUnderTest.validateUser(RandomString.make(64)));
	}
	
	
	
	
}