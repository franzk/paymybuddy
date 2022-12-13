package com.pmb.paymybuddy.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;
import com.pmb.paymybuddy.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

	@InjectMocks
	private BankService serviceUnderTest;

	@Mock
	private TransactionRepository transactionRepository;

	@Mock
	private UserRepository userRepository;

	@Test
	void createTransferTest() throws NegativeTransactionException, NotEnoughCreditException {

		// Arrange
		User sender = new User();
		sender.setBalance(1000);
		double testAmount = 100;

		// Act
		serviceUnderTest.receiveFromBank(sender, testAmount);

		// Assert
		verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	void createTransferTestWithNegativeTransactionException() {

		// Arrange
		User sender = new User();
		sender.setBalance(1000);
		double testAmount = -100;

		// Act + Assert
		assertThrows(NegativeTransactionException.class, () -> serviceUnderTest.receiveFromBank(sender, testAmount));
	}
	
	@Test
	void sendToBankTest() throws NegativeTransactionException, NotEnoughCreditException {

		// Arrange
		User sender = new User();
		sender.setBalance(1000);
		double testAmount = 100;

		// Act
		serviceUnderTest.sendToBank(sender, testAmount);

		// Assert
		verify(userRepository, times(1)).save(any(User.class));
	}
	
	@Test
	void sendToBankTestWithNegativeTransactionException() {

		// Arrange
		User sender = new User();
		sender.setBalance(1000);
		double testAmount = -100;

		// Act + Assert
		assertThrows(NegativeTransactionException.class, () -> serviceUnderTest.sendToBank(sender, testAmount));
	}
	
	@Test
	void sendToBankTestWithNotEnoughCreditException() {

		// Arrange
		User sender = new User();
		sender.setBalance(10);
		double testAmount = 100;

		// Act + Assert
		assertThrows(NotEnoughCreditException.class, () -> serviceUnderTest.sendToBank(sender, testAmount));
	}
}
