package com.pmb.paymybuddy.configuration;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import net.bytebuddy.utility.RandomString;

@ExtendWith(MockitoExtension.class)
public class MailSenderConfigTest {
	
	@InjectMocks
	MailSenderConfig classUnderTest;
	
	
	@BeforeEach
	public void setup() {
		ReflectionTestUtils.setField(classUnderTest, "smtpSSLEnable", RandomString.make(64)); // Mock private field smtpSSLEnable
	}
	
	@Test
	void javaMailSenderTest() {
		
		// Arrange
		
		// Act
		JavaMailSender result = classUnderTest.javaMailSender();
		
		// Assert
		assertThat(result).isNotNull();
	}

}
