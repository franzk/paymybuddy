package com.pmb.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.BankService;

@RestController
@RequestMapping("/bank")
public class BankController {
	
	@Autowired
	private BankService bankService;
	
	@Autowired
	private AuthenticationService authenticationService;
	
	@PostMapping("/from")
	public ResponseEntity<String> fromBank(@RequestBody TransactionDto bankDto) throws UserNotFoundException, NegativeTransactionException {
		bankService.receiveFromBank(authenticationService.getLoggedUser(), bankDto.getAmount());
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
	@PostMapping("/to")
	public ResponseEntity<String> toBank(@RequestBody TransactionDto bankDto) throws NegativeTransactionException, NotEnoughCreditException, UserNotFoundException  {
		bankService.sendToBank(authenticationService.getLoggedUser(), bankDto.getAmount());
		return new ResponseEntity<>("", HttpStatus.OK);
	}
	
}
