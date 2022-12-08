package com.pmb.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmb.paymybuddy.dto.UserDto;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerTestIT {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private static final String loggedUserEmail = "user1@example.com";
	private static final String populateScriptFilePath = "file:./database_files/integration_test_data/populateDB.sql";
	
	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	ObjectMapper mapper;
	
	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	@WithMockUser(username = loggedUserEmail)
	@Sql(scripts = populateScriptFilePath)
	void userTestIT() throws Exception {

		// Arrange
		User loggedUser = userRepository.findByEmail(loggedUserEmail).get();
		UserDto testDto = new UserDto();
		testDto.setBalance(loggedUser.getBalance());
		testDto.setEmail(loggedUser.getEmail());
		testDto.setName(loggedUser.getName());
		
		String expectedBody = mapper.writeValueAsString(testDto);
		
		// Act
		// Assert
		mockMvc.perform(get("/user")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(expectedBody));
				
	}
	
	@Test
	@Transactional
	@WithMockUser(username = loggedUserEmail)
	@Sql(scripts = populateScriptFilePath)
	void changerUserNameTestIT() throws Exception {

		// Arrange
		UserDto testDto = new UserDto();
		String newName = RandomString.make(64); 
		testDto.setName(newName);
		
		String requestJson = mapper.writeValueAsString(testDto);
		
		// Act
		// Assert
		mockMvc.perform(put("/user").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andExpect(status().isOk());
		
		User loggedUser = userRepository.findByEmail(loggedUserEmail).get();
		assertThat(loggedUser.getName()).isEqualTo(newName);
	}

}
