package com.pmb.paymybuddy.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Random;

import net.bytebuddy.utility.RandomString;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {

	@InjectMocks
	TransactionService serviceUnderTest;

	@Mock
	private TransactionRepository transactionRepository;

	@Test
	void getTransactionsBySenderTest() {
		// Arrange
		User testUser = new User();
		testUser.setEmail(RandomString.make(64));
		testUser.setName(RandomString.make(64));
		testUser.setBalance(new Random().nextDouble());

		// Act
		serviceUnderTest.getTransactionsBySender(testUser);

		// Assert
		verify(transactionRepository, times(1)).findBySender(testUser);
	}

	@Test
	void getTransactionsByRecipientTest() {

		// Arrange
		User testUser = new User();
		testUser.setEmail(RandomString.make(64));
		testUser.setName(RandomString.make(64));
		testUser.setBalance(new Random().nextDouble());

		// Act
		serviceUnderTest.getTransactionsByRecipient(testUser);

		// Assert
		verify(transactionRepository, times(1)).findByRecipient(testUser);
	}

	@Test
	void getUserTransactionsTest() {

		// Arrange
		User testUser = new User();
		int testId = new Random().nextInt();
		testUser.setUserId(testId);
		Pageable pageable = PageRequest.of(0, 1);

		// Act
		serviceUnderTest.getUserTransactions(testUser, pageable);

		// Assert
		verify(transactionRepository, times(1)).getUserTransactions(testId, pageable);
	}

}
