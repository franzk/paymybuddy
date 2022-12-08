package com.pmb.paymybuddy.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.pmb.paymybuddy.dto.NewTransactionDto;
import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.TransferService;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
class TransferControllerTest {

	@InjectMocks
	TransferController controllerUnderTest;
	
	@Mock
	private TransferService transferService;

	@Mock
	private AuthenticationService authenticationService;
	
	@Test
	void addTransactionTest() throws UserNotFoundException, NotEnoughCreditException, NegativeTransactionException {
		
		// Arrange
		String testEmail = RandomString.make(64);
		double testAmount = new Random().nextDouble();
		NewTransactionDto testDto = new NewTransactionDto();
		testDto.setRecipientEmail(testEmail);
		testDto.setAmount(testAmount);
		
		// Act
		controllerUnderTest.createTransfer(testDto);
		
		// Assert
		verify(transferService, times(1)).createTransfer(any(), eq(testEmail), eq(testAmount));
	}
	
}
