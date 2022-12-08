package com.pmb.paymybuddy.exception;

import java.sql.SQLIntegrityConstraintViolationException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		StringBuilder body = new StringBuilder();
		for (ConstraintViolation<?> cv : ex.getConstraintViolations()) {
			body.append(cv.getMessage() + "\n");
		}
		return handleExceptionInternal(ex, body.toString(), new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);

	}

	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	protected ResponseEntity<Object> handleSQLIntegrityConstraintViolationException(
			SQLIntegrityConstraintViolationException ex, WebRequest request) {
		String body = ex.getMessage() + "\n";
		return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(UserNotFoundException.class)
	protected ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Cet utilisateur n'existe pas.", new HttpHeaders(), HttpStatus.NOT_FOUND,
				request);
	}

	@ExceptionHandler(SelfFriendshipException.class)
	protected ResponseEntity<Object> handleSelfFriendshipException(SelfFriendshipException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Vous ne pouvez pas être ami avec vous même !", new HttpHeaders(),
				HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(FriendNotFoundException.class)
	protected ResponseEntity<Object> handleFriendNotFoundException(FriendNotFoundException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Cet ami n'existe pas.", new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(UserAlreadyExistException.class)
	protected ResponseEntity<Object> handleUserAlreadyExistException(UserAlreadyExistException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Cet utilisateur existe déjà.", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE,
				request);
	}

	@ExceptionHandler(SendEmailException.class)
	protected ResponseEntity<Object> handleSendEmailException(SendEmailException ex, WebRequest request) {
		return handleExceptionInternal(ex,
				"Impossible d'envoyer l'email de validation. Veuillez vérifier l'adresse email.", new HttpHeaders(),
				HttpStatus.NOT_ACCEPTABLE, request);
	}

	@ExceptionHandler(AlreadyFriendException.class)
	protected ResponseEntity<Object> handleAlreadyFriendException(AlreadyFriendException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Cet utilisateur est déjà votre ami !.", new HttpHeaders(),
				HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	@ExceptionHandler(NotEnoughCreditException.class)
	protected ResponseEntity<Object> handleNotEnoughCreditException(NotEnoughCreditException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Vous n'avez pas assez d'argent pour exécuter cette transaction !.", new HttpHeaders(),
				HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	@ExceptionHandler(NegativeTransactionException.class)
	protected ResponseEntity<Object> handleNegativeTransactionException(NegativeTransactionException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Le montant de la transaction doit être positif !.", new HttpHeaders(),
				HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	@ExceptionHandler(TooLateEmailValidationException.class)
	protected ResponseEntity<Object> handleTooLateEmailValidationException(TooLateEmailValidationException ex, WebRequest request) {
		return handleExceptionInternal(ex, "Le délai de validation d'une heure est expiré. Veuillez recommencer !", new HttpHeaders(),
				HttpStatus.NOT_ACCEPTABLE, request);
	}
	
}
