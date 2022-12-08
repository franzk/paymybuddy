package com.pmb.paymybuddy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;

@Service
public class TransactionService {

	@Autowired
	private TransactionRepository transactionRepository;

	public Iterable<Transaction> getTransactionsBySender(User user) {
		return transactionRepository.findBySender(user);
	}

	public Iterable<Transaction> getTransactionsByRecipient(User user) {
		return transactionRepository.findByRecipient(user);
	}

	public Page<TransactionDto> getUserTransactions(User user, Pageable pageable) {
		return transactionRepository.getUserTransactions(user.getUserId(), pageable);
	}

}
