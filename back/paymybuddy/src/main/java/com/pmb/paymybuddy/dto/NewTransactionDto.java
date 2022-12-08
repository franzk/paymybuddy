package com.pmb.paymybuddy.dto;

import lombok.Data;

@Data
public class NewTransactionDto {

	String recipientEmail;
	
	double amount;
}
