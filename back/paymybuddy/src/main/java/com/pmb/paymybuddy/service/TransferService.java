package com.pmb.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.exception.NegativeTransactionException;
import com.pmb.paymybuddy.exception.NotEnoughCreditException;
import com.pmb.paymybuddy.exception.UserNotFoundException;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.TypeTransaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;

@Service
public class TransferService {

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserService userService;

	@Transactional(rollbackOn = { IllegalArgumentException.class, OptimisticLockingFailureException.class })
	public Transaction createTransfer(User sender, String recipientEmail, double amount)
			throws UserNotFoundException, NotEnoughCreditException, NegativeTransactionException {

		if (amount < 0) {
			throw new NegativeTransactionException();
		}
		
		double amountWithFee = amount * (1 + Constants.FEE); // la comission est imputée à l'envoyeur

		if (sender.getBalance() < amountWithFee) {
			throw new NotEnoughCreditException();
		}

		

		Optional<User> optionalRecipient = userService.getUserByEmail(recipientEmail);

		if (optionalRecipient.isEmpty()) {
			throw new UserNotFoundException();
		}

		User recipient = optionalRecipient.get();

		Transaction newTransaction = new Transaction();
		newTransaction.setType(TypeTransaction.TRANSFER);
		newTransaction.setSender(sender);
		newTransaction.setRecipient(recipient);
		newTransaction.setDate(LocalDateTime.now());
		
		newTransaction.setAmountReceived(amount);       // on crédite le destinataire du montant
		newTransaction.setAmountSent(amountWithFee);    // on débite l'envoyeur du montant + la commission
		
		sender.setBalance(sender.getBalance() - amountWithFee);
		userService.save(sender);

		recipient.setBalance(recipient.getBalance() + amount);
		userService.save(recipient);

		return transactionRepository.save(newTransaction);
	}

}
