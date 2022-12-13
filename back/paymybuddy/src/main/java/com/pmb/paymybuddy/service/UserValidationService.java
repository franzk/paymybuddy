package com.pmb.paymybuddy.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.exception.TooLateEmailValidationException;
import com.pmb.paymybuddy.exception.UnverifiedUserNotFoundException;
import com.pmb.paymybuddy.model.UnverifiedUser;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UnverifiedUserRepository;
import com.pmb.paymybuddy.repository.UserRepository;

@Service
public class UserValidationService {

	@Autowired
	private UnverifiedUserRepository unverifiedUserRepository;

	@Autowired
	private UserRepository userRepository;

	@Transactional(rollbackOn = { IllegalArgumentException.class, OptimisticLockingFailureException.class  })
	public User validateUser(String validationToken)
			throws TooLateEmailValidationException, UnverifiedUserNotFoundException {

		UnverifiedUser unverifiedUser = getUnverifiedUser(validationToken);

		// Si le token a été généré il y a plus d'une heure, c'est trop tard
		if (unverifiedUser.getDateTime().isBefore(LocalDateTime.now().minusHours(1))) {
			throw new TooLateEmailValidationException();
		}

		// Enregistrement du nouvel user et suppression de la table des unverified
		User user = new User();

		user.setEmail(unverifiedUser.getEmail());
		user.setName(unverifiedUser.getName());
		user.setPassword(unverifiedUser.getPassword());

		unverifiedUserRepository.delete(unverifiedUser);
		return userRepository.save(user);
	}

	
	private UnverifiedUser getUnverifiedUser(String validationToken) throws UnverifiedUserNotFoundException {
		List<UnverifiedUser> users = (List<UnverifiedUser>) unverifiedUserRepository
				.getByValidationTokenOrderByDateTimeDesc(validationToken);
		if (users.isEmpty()) {
			throw new UnverifiedUserNotFoundException();
		} else {
			return users.get(0);
		}
	}
}
