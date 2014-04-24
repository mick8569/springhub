package com.mjeanroy.springhub.mail;

import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

public class AsyncMailSender extends JavaMailSenderImpl {

	/**
	 * Send mail using an asynchronous task.
	 *
	 * @param simpleMessage Message.
	 * @throws MailException
	 */
	@Async
	public void sendAsync(SimpleMailMessage simpleMessage) throws MailException {
		super.send(simpleMessage);
	}

	/**
	 * Send mail using an asynchronous task.
	 *
	 * @param simpleMessages Message.
	 * @throws MailException
	 */
	@Async
	public void sendAsync(SimpleMailMessage[] simpleMessages)
			throws MailException {
		super.send(simpleMessages);
	}
}
