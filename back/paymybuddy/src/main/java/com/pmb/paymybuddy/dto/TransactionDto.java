package com.pmb.paymybuddy.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionDto {

	LocalDateTime date;	
	double amount;
	String type;

	String senderEmail;
	String senderName;
		
	String recipientEmail;
	String recipientName;
	
	public TransactionDto(LocalDateTime date, double amount, String type, String senderEmail,
			String senderName, String recipientEmail, String recipientName) {
	
		this.date = date;
		this.amount = amount;
		this.type = type;
		
		this.senderEmail = senderEmail;
		this.senderName = senderName;
		
		this.recipientEmail = recipientEmail;
		this.recipientName = recipientName;
	}
	

	
}
