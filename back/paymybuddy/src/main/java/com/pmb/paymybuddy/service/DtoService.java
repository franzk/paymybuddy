package com.pmb.paymybuddy.service;

import org.springframework.stereotype.Service;

import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.model.User;

@Service
public class DtoService {
	
	public UserDto buildUserDto(User user) {
		UserDto result = new UserDto();
		result.setName(user.getName());
		result.setEmail(user.getEmail());
		result.setBalance(user.getBalance());

		return result;

	}

}
