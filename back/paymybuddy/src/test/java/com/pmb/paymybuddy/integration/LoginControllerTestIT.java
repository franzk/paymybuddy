package com.pmb.paymybuddy.integration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class LoginControllerTestIT {

	private static final String loggedUserEmail = "user1@example.com";
	private static final String populateScriptFilePath = "file:./integration_test_data/populateDB.sql";

	@Autowired
	public MockMvc mockMvc;
	
	@Test
	@Transactional
	@WithMockUser(username = loggedUserEmail)
	@Sql(scripts = populateScriptFilePath)
	void loginIT() throws Exception {
	
		
		mockMvc.perform(get("/perform_login")).andExpect(status().isOk())
		.andExpect(content().string(loggedUserEmail));
		
	}

}
