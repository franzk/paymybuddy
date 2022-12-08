package com.pmb.paymybuddy.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.TypeTransaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.BankService;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class BankControllerTest {

	@InjectMocks
	BankController controllerUnderTest;

	@Mock
	private BankService bankService;

	@Mock
	private AuthenticationService authenticationService;


	@Test
	void fromBankTest() throws UserNotFoundException, NegativeTransactionException {

		// Arrange
		double testAmount = new Random().nextDouble();
		TransactionDto testDto = new TransactionDto(LocalDateTime.now(), testAmount, TypeTransaction.BANK,
				RandomString.make(64), RandomString.make(64), RandomString.make(64), RandomString.make(64));

		User testUser = new User();
		when(authenticationService.getLoggedUser()).thenReturn(testUser);

		// Act
		controllerUnderTest.fromBank(testDto);

		// Arrange
		verify(bankService, times(1)).receiveFromBank(testUser, testAmount);

	}
	
	@Test
	void toBankTest() throws UserNotFoundException, NegativeTransactionException, NotEnoughCreditException {

		// Arrange
		double testAmount = new Random().nextDouble();
		TransactionDto testDto = new TransactionDto(LocalDateTime.now(), testAmount, TypeTransaction.BANK,
				RandomString.make(64), RandomString.make(64), RandomString.make(64), RandomString.make(64));

		User testUser = new User();
		when(authenticationService.getLoggedUser()).thenReturn(testUser);

		// Act
		controllerUnderTest.toBank(testDto);

		// Arrange
		verify(bankService, times(1)).sendToBank(testUser, testAmount);

	}
}
