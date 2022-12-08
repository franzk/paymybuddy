package com.pmb.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

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
import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;
import com.pmb.paymybuddy.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransferControllerTestIT {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private static final String loggedUserEmail = "user1@example.com";
	private static final String populateScriptFilePath = "file:./integration_test_data/populateDB.sql";

	@Autowired
	public MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Test
	@Transactional
	@WithMockUser(username = loggedUserEmail)
	@Sql(scripts = populateScriptFilePath)
	void createTransferTestIT() throws Exception {

		// Arrange
		double testAmount = new Random().nextDouble() * 1000;

		User loggedUser = userRepository.findByEmail(loggedUserEmail).get();
		loggedUser.setBalance(testAmount + 1000);
		List<User> users = (List<User>) userRepository.findAll();
		User recipient = users.get(2);

		TransactionDto testDto = new TransactionDto(null, testAmount, null, null, null, null, null);
		testDto.setRecipientEmail(recipient.getEmail());

		String requestJson = mapper.writeValueAsString(testDto);

		LocalDateTime processTime = LocalDateTime.now();

		// Act
		// Assert
		mockMvc.perform(post("/transfer").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andExpect(status().isCreated());

		List<Transaction> userTransactions = (List<Transaction>) transactionRepository.findBySender(loggedUser);
		Transaction result = userTransactions.get(0);
		assertEquals(result.getAmountReceived(), testAmount, 0.01);
		assertThat(result.getDate()).isAfter(processTime);

	}

}
