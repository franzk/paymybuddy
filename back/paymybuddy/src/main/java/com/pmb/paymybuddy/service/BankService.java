package com.pmb.paymybuddy.service;

import java.time.LocalDateTime;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.TypeTransaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;

@Service
public class BankService {
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional(rollbackOn = { NegativeTransactionException.class })
	public Transaction receiveFromBank(User user, double amount) throws NegativeTransactionException {

		if (amount <0) {
			throw new NegativeTransactionException();
		}
		
		double amountPostFee = amount * (1 - Constants.FEE); // on retient la commission
		
		Transaction newTransaction = new Transaction();
		newTransaction.setSender(user);
		newTransaction.setRecipient(user);
		newTransaction.setDate(LocalDateTime.now());
		newTransaction.setAmountReceived(amountPostFee);
		newTransaction.setAmountSent(0);
		newTransaction.setType(TypeTransaction.BANK);

		user.setBalance(user.getBalance() + amountPostFee);
		userService.save(user);
		
		return transactionRepository.save(newTransaction);

	}

	
	@Transactional(rollbackOn = { NegativeTransactionException.class, NotEnoughCreditException.class })
	public Transaction sendToBank(User user, double amount) throws NegativeTransactionException, NotEnoughCreditException {
		
		if (amount < 0) {
			throw new NegativeTransactionException();
		}
		
		if (user.getBalance() < amount) {
			throw new NotEnoughCreditException();
		}
		
		// pas de calcul de commission ici. La banque recevera l'amount - la comission
		
		Transaction newTransaction = new Transaction();
		newTransaction.setSender(user);
		newTransaction.setRecipient(user);
		newTransaction.setDate(LocalDateTime.now());
		newTransaction.setAmountSent(amount);
		newTransaction.setAmountReceived(0);
		newTransaction.setType(TypeTransaction.BANK);
		
		user.setBalance(user.getBalance() - amount);
		userService.save(user);
		
		return transactionRepository.save(newTransaction);
	}
	
}
