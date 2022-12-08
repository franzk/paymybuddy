package com.pmb.paymybuddy.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;

import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.TransactionService;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

	@InjectMocks
	TransactionController controllerUnderTest;
	
	@Mock
	private AuthenticationService authenticationService;
	
	@Mock
	private TransactionService transactionService;
	
	@Test
	void getUserTransactionsTest() throws UserNotFoundException {
		
		// Arrange
		List<TransactionDto> testList = new ArrayList<>();
		Page<TransactionDto> testPage = new PageImpl<>(testList);
		when(transactionService.getUserTransactions(any(), any())).thenReturn(testPage);
		
		// Act
		ResponseEntity<Map<String, Object>> result = controllerUnderTest.getUserTransactions(0);
		
		// Assert
		assertThat(result.getBody()).isNotNull();
		
	}
	
}
