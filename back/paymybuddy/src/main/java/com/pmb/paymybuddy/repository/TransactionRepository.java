package com.pmb.paymybuddy.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer>{

	public Iterable<Transaction> findBySender(User user);

	public Iterable<Transaction> findByRecipient(User user);
	
	@Query(nativeQuery = true)
	public Page<TransactionDto> getUserTransactions(int userId, Pageable pageable);
	
	
}
