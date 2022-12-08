package com.pmb.paymybuddy.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;


@Configuration
@PropertySource(value = "file:./external_config/mailsender.properties")
public class MailSenderConfig {

	@Value("${spring.mail.host}")
	private String host;

	@Value("${spring.mail.port}")
	private int port;

	@Value("${spring.mail.username}")
	private String username;

	@Value("${spring.mail.password}")
	private String password;

	@Value("${spring.mail.properties.mail.smtp.auth}")
	private String smtpAuth;

	@Value("${spring.mail.properties.mail.smtp.ssl.enable}")
	private String smtpSSLEnable;

	@Bean
	public JavaMailSender javaMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);
		mailSender.setProtocol("smtp");
		Properties javaMailProps = new Properties();
		javaMailProps.setProperty("mail.smtp.ssl.enable", smtpSSLEnable);
		javaMailProps.setProperty("mail.smtp.timeout", "25000");
		mailSender.setJavaMailProperties(javaMailProps);
		return mailSender;
	}

}
