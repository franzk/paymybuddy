package com.pmb.paymybuddy.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@GetMapping("/perform_login")
	public String login(HttpServletRequest request) {
		Principal userPrincipal = request.getUserPrincipal();
		return userPrincipal.getName();
	}

}
