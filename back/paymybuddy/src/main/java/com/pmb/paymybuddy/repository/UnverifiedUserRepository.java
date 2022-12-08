package com.pmb.paymybuddy.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.pmb.paymybuddy.model.UnverifiedUser;

@Repository
public interface UnverifiedUserRepository extends CrudRepository<UnverifiedUser, Integer> {

	public Iterable<UnverifiedUser> getByValidationTokenOrderByDateTimeDesc(String verificationCode);	
	
}
