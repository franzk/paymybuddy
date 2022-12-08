package com.pmb.paymybuddy.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class ControllerAdvisorTest {
	
	@InjectMocks
	private ControllerAdvisor classUnderTest;
	
	@Test
	void handleConstraintViolationExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleConstraintViolationException(new ConstraintViolationException(new HashSet<>()), null);
		//Assert
		assertThat(result.getBody()).isNotNull();
	}

	@Test
	void handleSQLIntegrityConstraintViolationExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleSQLIntegrityConstraintViolationException(new SQLIntegrityConstraintViolationException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleUserNotFoundExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleUserNotFoundException(new UserNotFoundException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleSelfFriendshipExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleSelfFriendshipException(new SelfFriendshipException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleFriendNotFoundExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleFriendNotFoundException(new FriendNotFoundException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleUserAlreadyExistExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleUserAlreadyExistException(new UserAlreadyExistException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleMailSendExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleSendEmailException(new SendEmailException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleAlreadyFriendExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleAlreadyFriendException(new AlreadyFriendException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}

	@Test
	void handleNotEnoughCreditExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleNotEnoughCreditException(new NotEnoughCreditException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleNegativeTransactionExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleNegativeTransactionException(new NegativeTransactionException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
	@Test
	void handleTooLateEmailValidationExceptionTest() {
		// Arrange
		// Act
		ResponseEntity<Object> result = classUnderTest.handleTooLateEmailValidationException(new TooLateEmailValidationException() , null);
		// Assert
		assertThat(result.getBody()).isNotNull();
	}
	
}
