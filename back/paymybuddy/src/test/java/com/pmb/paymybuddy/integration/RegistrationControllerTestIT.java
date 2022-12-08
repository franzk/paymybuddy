package com.pmb.paymybuddy.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pmb.paymybuddy.dto.UserRegistrationDto;
import com.pmb.paymybuddy.model.UnverifiedUser;
import com.pmb.paymybuddy.model.User;
import com.pmb.paymybuddy.repository.UnverifiedUserRepository;
import com.pmb.paymybuddy.repository.UserRepository;

import net.bytebuddy.utility.RandomString;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class RegistrationControllerTestIT {

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	private static final String populateScriptFilePath = "file:./database_files/integration_test_data/populateDB.sql";

	@Autowired
	public MockMvc mockMvc;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private UnverifiedUserRepository unverifiedUserRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	@Transactional
	@Sql(scripts = populateScriptFilePath)
	void registerTestIT() throws Exception {

		// Arrange
		String testEmail = "aa@bb.com"; // cette adresse doit vraiment exister sinon l'envoi de mail échoue et renvoie
										// une exception
		String testName = RandomString.make(64);

		UserRegistrationDto testDto = new UserRegistrationDto();
		testDto.setName(testName);
		testDto.setPassword(RandomString.make(64));
		testDto.setEmail(testEmail);

		String requestJson = mapper.writeValueAsString(testDto);

		// Act
		// Assert
		mockMvc.perform(post("/register").contentType(APPLICATION_JSON_UTF8).content(requestJson))
				.andExpect(status().isAccepted());

		List<UnverifiedUser> result = (List<UnverifiedUser>) unverifiedUserRepository.findAll();
		UnverifiedUser resultUnverifiedUser = result.get(0);
		assertThat(resultUnverifiedUser.getName()).isEqualTo(testName);

	}

	@Test
	@Transactional
	@Sql(scripts = populateScriptFilePath)
	void validateUserTestIT() throws Exception {

		// Arrange
		String testEmail = "aa@bb.com";
		String testName = RandomString.make(64);

		UnverifiedUser unverifiedUser = new UnverifiedUser();
		unverifiedUser.setDateTime(LocalDateTime.now());
		unverifiedUser.setName(testName);
		unverifiedUser.setEmail(testEmail);
		unverifiedUser.setPassword(RandomString.make(64));
		String testVaildationToken = RandomString.make(64);
		unverifiedUser.setValidationToken(testVaildationToken);

		unverifiedUserRepository.save(unverifiedUser);

		// Act
		// Assert
		mockMvc.perform(get("/validateUser").param("validationToken", testVaildationToken))
				.andExpect(status().isCreated());

		Optional<User> result = userRepository.findByEmail(testEmail);
		assertThat(result).isPresent();
		assertThat(result.get().getName()).isEqualTo(testName);
	}

}
