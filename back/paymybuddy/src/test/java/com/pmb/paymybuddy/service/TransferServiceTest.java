package com.pmb.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

	@InjectMocks
	private TransferService serviceUnderTest;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private UserService userService;

	@Mock
	private AuthenticationService authenticationService;

	@Test
	void createTransactionTest() throws UserNotFoundException, NotEnoughCreditException, NegativeTransactionException {

		// Arrange
		User sender = new User();
		sender.setBalance(1000);
		when(userService.getUserByEmail(any())).thenReturn(Optional.of(sender));

		// Act
		serviceUnderTest.createTransfer(sender, RandomString.make(64), 100);

		// Assert
		verify(userService, times(2)).save(any(User.class));

	}

	@Test
	void createTransactionWithNotEnoughCreditException() {

		// Arrange
		User sender = new User();
		sender.setBalance(10);

		// Act + Assert
		assertThrows(NotEnoughCreditException.class,
				() -> serviceUnderTest.createTransfer(sender, RandomString.make(64), 100));
	}

	@Test
	void createTransactionWithNegativeTransactionException() {

		// Arrange
		User sender = new User();

		// Act + Assert
		assertThrows(NegativeTransactionException.class,
				() -> serviceUnderTest.createTransfer(sender, RandomString.make(64), -100));
	}

	@Test
	void createTransactionWithUserNotFoundException() {

		// Arrange
		User sender = new User();
		sender.setBalance(1000);
		when(userService.getUserByEmail(any())).thenReturn(Optional.empty());

		// Act + Assert
		assertThrows(UserNotFoundException.class,
				() -> serviceUnderTest.createTransfer(sender, RandomString.make(64), 100));
	}

}
