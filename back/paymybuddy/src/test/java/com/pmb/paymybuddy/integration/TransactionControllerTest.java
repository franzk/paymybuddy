package com.pmb.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmb.paymybuddy.controller.TransactionController;
import com.pmb.paymybuddy.dto.TransactionDto;
import com.pmb.paymybuddy.model.Transaction;
import com.pmb.paymybuddy.model.TypeTransaction;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.TransactionRepository;
import com.pmb.paymybuddy.repository.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class TransactionControllerTest {

	private static final String loggedUserEmail = "user1@example.com";
	private static final String populateScriptFilePath = "file:./database_files/integration_test_data/populateDB.sql";

	@Autowired
	public MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	@WithMockUser(username = loggedUserEmail)
	@Sql(scripts = populateScriptFilePath)
	void getUserTransactionsTestIT() throws Exception {

		// Arrange
		List<User> users = (List<User>) userRepository.findAll();

		int testRecordsCount = 10;
		double lastAmount = 0;

		for (int i = 0; i < testRecordsCount; i++) {
			Transaction newTransaction = new Transaction();
			lastAmount = (new Random().nextDouble()) * 1000;
			newTransaction.setAmountReceived(lastAmount);
			newTransaction.setAmountSent(lastAmount);
			newTransaction.setDate(LocalDateTime.now().plusMinutes(i));
			newTransaction.setType(TypeTransaction.TRANSFER);
			newTransaction.setRecipient(users.get(new Random().nextInt(users.size())));
			newTransaction.setSender(userRepository.findByEmail(loggedUserEmail).get());
			transactionRepository.save(newTransaction);
		}

		// Act
		ResultActions resultActions = mockMvc.perform(get("/transactions").param("page", "0")).andDo(print())
				.andExpect(status().isOk());

		
		// Assert
		MvcResult result = resultActions.andReturn();

		String contentAsString = result.getResponse().getContentAsString();
		Map<String, Object> resultData = mapper.readValue(contentAsString, new TypeReference<Map<String, Object>>() {});

		String transactionsInResultData = mapper.writeValueAsString(resultData.get("transactions"));
		List<TransactionDto> resultTransactions = mapper.readValue(transactionsInResultData, new TypeReference<List<TransactionDto>>() {});
		
		int itemsCount = Integer.parseInt(mapper.writeValueAsString(resultData.get("totalItems")));
		assertThat(itemsCount).isEqualTo(testRecordsCount);

		assertThat(resultTransactions).hasSize(TransactionController.ITEMS_PER_PAGE);
		assertEquals(-lastAmount, resultTransactions.get(0).getAmount(), 0.01); // le premier dto est le dernier
																					// enregistrement de la table Transaction
																					// (requête trié par Date DESC)

	}

}
