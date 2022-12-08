package com.pmb.paymybuddy.service;

import java.io.UnsupportedEncodingException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@PropertySource(value = "file:./external_config/mailsender.properties")
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Value("${com.pmb.paymybuddy.emailaddress}")
	private String fromAddress;

	public void sendEmail(String toAddress, String senderName, String subject, String content)
			throws UnsupportedEncodingException, MessagingException {

		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);

		helper.setText(content, true);

		mailSender.send(message);

		log.info("Email de validation envoyé à " + toAddress);
	}

}
