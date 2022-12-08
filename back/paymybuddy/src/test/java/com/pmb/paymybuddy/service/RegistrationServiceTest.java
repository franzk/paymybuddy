package com.pmb.paymybuddy.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.util.Optional;

import javax.mail.MessagingException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pmb.paymybuddy.dto.UserRegistrationDto;
import com.pmb.paymybuddy.exception.SendEmailException;
import com.pmb.paymybuddy.exception.UserAlreadyExistException;
import com.pmb.paymybuddy.model.UnverifiedUser;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UnverifiedUserRepository;
import com.pmb.paymybuddy.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class RegistrationServiceTest {
	
	@InjectMocks
	@Spy // for doNothing in createUnverifiedUserTest
	RegistrationService serviceUnderTest;

	
	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;
	
	@Mock
	private UnverifiedUserRepository unverifiedUserRepository;

	@Mock
	UserService userService;
		
	@Mock
	EmailService emailSendService;
	
	@Test
	void startRegistrationTest() throws UnsupportedEncodingException, SendEmailException, UserAlreadyExistException, MessagingException {
		
		// Arrange
		String email = RandomString.make(64);
		String name = RandomString.make(64);
		String password = RandomString.make(64);
		
		UserRegistrationDto testDto = new UserRegistrationDto();
		testDto.setEmail(email);
		testDto.setName(name);
		testDto.setPassword(password);
		
		doNothing().when(serviceUnderTest).sendVerificationEmail(any());
		
		// Act
		serviceUnderTest.startRegistation(testDto);
		
		// Assert
		verify(serviceUnderTest, times(1)).createUnverifiedUser(email, name, password);
	}
	
	@Test
	void createUnverifiedUserTest() throws UnsupportedEncodingException, SendEmailException, UserAlreadyExistException, MessagingException {
		
		// Arrange
		String email = RandomString.make(64);
		String name = RandomString.make(64);
		String password = RandomString.make(64);
		
		UnverifiedUser testUnverifiedUser = new UnverifiedUser();
		testUnverifiedUser.setEmail(email);
		testUnverifiedUser.setName(name);
		testUnverifiedUser.setPassword(password);
		
		when(passwordEncoder.encode(any())).thenReturn(password);
		doNothing().when(serviceUnderTest).sendVerificationEmail(any());
		when(unverifiedUserRepository.save(any())).thenReturn(testUnverifiedUser);
		
		// Act 
		UnverifiedUser result = serviceUnderTest.createUnverifiedUser(email, name, password);
		
		// Assert
		assertThat(result.getEmail()).isEqualTo(email);
		assertThat(result.getName()).isEqualTo(name);
		assertThat(result.getPassword()).isEqualTo(password);
	}

	@Test
	void createUnverifiedUserTestWithUserAlreadyExistException() {
		
		// Arrange
		when(userService.getUserByEmail(any())).thenReturn(Optional.of(new User()));
		
		// Act + Assert
		assertThrows(UserAlreadyExistException.class, () -> serviceUnderTest.createUnverifiedUser(any(), any(), any()));
	}

	@Test
	void sendVerificationEmailTest() throws UnsupportedEncodingException, MessagingException, SendEmailException {
		
		// Arrange
		doNothing().when(emailSendService).sendEmail(any(), any(), any(), any());
	
		// Act 
		serviceUnderTest.sendVerificationEmail(new UnverifiedUser());
		
		// Assert
		verify(emailSendService, (times(1))).sendEmail(any(), any(), any(), any());
	}
	
}
