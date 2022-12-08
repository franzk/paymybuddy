package com.pmb.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.List;

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

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FriendControllerTestIT {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private static final String loggedUserEmail = "user1@example.com";
	private static final String populateScriptFilePath = "file:./integration_test_data/populateDB.sql";

	@Autowired
	public MockMvc mockMvc;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	ObjectMapper mapper;
	
	@Test
	@Transactional
	@WithMockUser(username = loggedUserEmail)
	@Sql(scripts = populateScriptFilePath)
	void getFriendsTestIT() throws Exception {
		
		// Arrange
		User loggedUser = userRepository.findByEmail(loggedUserEmail).get();
		User testFriend = userRepository.findById(2).get(); 
		
		loggedUser.addFriend(testFriend);
		userRepository.save(loggedUser);
		
		UserDto testDto = new UserDto();
		testDto.setEmail(testFriend.getEmail());
		testDto.setName(testFriend.getName());
		List<UserDto> testList = List.of(testDto);
		
		String expectedBody = mapper.writeValueAsString(testList);
		
		// Act
		// Assert
		mockMvc.perform(get("/friends")
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().string(expectedBody));
	
	}
	
	@Test
	@Transactional
	@WithMockUser(username = loggedUserEmail)
	@Sql(scripts = populateScriptFilePath)
	void addFriendTestIT() throws Exception {
		
		// Arrange
		User newFriend = userRepository.findById(2).get();
		
		String requestJson = mapper.writeValueAsString(newFriend);
		
		// Act
		// Assert
		mockMvc.perform(post("/friend").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andExpect(status().isCreated());

		User loggedUser = userRepository.findByEmail(loggedUserEmail).get();
		assertThat(loggedUser.getFriends()).contains(newFriend);
	}

}
