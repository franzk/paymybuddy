package com.pmb.paymybuddy.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import net.bytebuddy.utility.RandomString;

@Data
@Entity
public class UnverifiedUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "unverified_user_id")
	private Integer unverifiedUserId;
	
	@Column(nullable=false)
	private String email;

	@Column(nullable=false)
	private String name;

	@Column(nullable=false)
	private String password = RandomString.make(64);
	
	@Column
	private String validationToken = RandomString.make(64);
	
	@Column
	private LocalDateTime dateTime = LocalDateTime.now();
	
}
