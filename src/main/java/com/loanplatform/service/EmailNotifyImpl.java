package com.loanplatform.service;

import org.springframework.stereotype.Component;

import com.loanplatform.pojo.EmailMessage;

@Component
public class EmailNotifyImpl implements Notifcation<EmailMessage> {

	@Override
	public Boolean send(EmailMessage data) {
		// TODO: Build email logic
		return true;
	}

}
