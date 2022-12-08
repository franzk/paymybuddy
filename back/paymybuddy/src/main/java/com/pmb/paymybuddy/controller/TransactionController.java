package com.pmb.paymybuddy.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.TransactionService;

@RestController
public class TransactionController {

	public static final int ITEMS_PER_PAGE = 3;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@GetMapping("/transactions")
	public ResponseEntity<Map<String, Object>> getUserTransactions(@RequestParam(defaultValue = "0") int page)
			throws UserNotFoundException {

		int size = ITEMS_PER_PAGE;

		Pageable pageable = PageRequest.of(page, size);

		Page<TransactionDto> pageTransactions = transactionService
				.getUserTransactions(authenticationService.getLoggedUser(), pageable);

		Map<String, Object> response = new HashMap<>();
		response.put("transactions", pageTransactions.getContent());
		response.put("currentPage", pageTransactions.getNumber());
		response.put("totalItems", pageTransactions.getTotalElements());
		response.put("totalPages", pageTransactions.getTotalPages());
		response.put("itemsPerPage", pageTransactions.getSize());

		return new ResponseEntity<>(response, HttpStatus.OK);

	}
}
