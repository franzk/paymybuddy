package com.pmb.paymybuddy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pmb.paymybuddy.dto.NewTransactionDto;
import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.service.AuthenticationService;
import com.pmb.paymybuddy.service.TransferService;

@RestController
public class TransferController {

	@Autowired
	private TransferService transferService;

	@Autowired
	private AuthenticationService authenticationService;

	@PostMapping("/transfer")
	public ResponseEntity<String> createTransfer(@RequestBody NewTransactionDto transferDto)
			throws UserNotFoundException, NotEnoughCreditException, NegativeTransactionException {
		transferService.createTransfer(authenticationService.getLoggedUser(), transferDto.getRecipientEmail(),
				transferDto.getAmount());
		return new ResponseEntity<>("", HttpStatus.CREATED);
	}

}
