package com.pmb.paymybuddy.service;

import java.io.UnsupportedEncodingException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.dto.UserRegistrationDto;
import com.pmb.paymybuddy.exception.SendEmailException;
import com.pmb.paymybuddy.exception.UserAlreadyExistException;
import com.pmb.paymybuddy.model.UnverifiedUser;
import com.pmb.paymybuddy.repository.UnverifiedUserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class RegistrationService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private EmailService emailSendService;

	@Autowired
	private UnverifiedUserRepository unverifiedUserRepository;

	@Autowired
	UserService userService;

	@Transactional(rollbackOn = { UnsupportedEncodingException.class, SendEmailException.class,
			UserAlreadyExistException.class })
	public UnverifiedUser startRegistation(UserRegistrationDto userRegistrationDto)
			throws SendEmailException, UserAlreadyExistException {

		return this.createUnverifiedUser(userRegistrationDto.getEmail(), userRegistrationDto.getName(),
				userRegistrationDto.getPassword());

	}

	public UnverifiedUser createUnverifiedUser(String email, String name, String password)
			throws UserAlreadyExistException, SendEmailException {

		if (userService.getUserByEmail(email).isPresent()) {
			throw new UserAlreadyExistException();
		}

		UnverifiedUser unverifiedUser = new UnverifiedUser();

		unverifiedUser.setEmail(email);
		unverifiedUser.setName(name);
		
		this.sendVerificationEmail(unverifiedUser);
	
		String encodedPassword = passwordEncoder.encode(password);
		unverifiedUser.setPassword(encodedPassword);

		return unverifiedUserRepository.save(unverifiedUser);
	}

	public void sendVerificationEmail(UnverifiedUser unverifiedUser) throws SendEmailException {

		log.info("envoi de mail à " + unverifiedUser.getEmail());
		
		String toAddress = unverifiedUser.getEmail();
		String senderName = "Pay My Buddy";
		String subject = "Vérification de l'adresse mail";
		String content = "Cher " + unverifiedUser.getName() + " ,<br>"
				+ "Cliquez sur le lien suivant pour terminer votre inscription à Pay My Buddy<br>" + "<h3><a href=\""
				+ "http://localhost:8080/validateUser?validationToken=" + unverifiedUser.getValidationToken()
				+ "\" target=\"_self\">VALIDATION</a></h3>" + "Attention ! Ce lien n'est valable qu'une heure."
				+ "Merci,<br>" + "L'équipe Pay My Buddy.";
		try {
			emailSendService.sendEmail(toAddress, senderName, subject, content);
		} catch (Exception ex) {
			log.info("Problème lors de l'envoi de mail à " + unverifiedUser.getEmail() + "\n" + ex.getMessage());
			throw new SendEmailException();
		}
	}

}
