package com.loanplatform.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.service.LoanService;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoanApprovalListener {
	@Autowired
	@Qualifier("LoanServiceImpl")
	private LoanService loanService;

	@RabbitListener(queues = "frontOffice.verify.queue")
	public void frontOfficeRecievedMessage(FrontOfficeMessage msg) {
		log.info("recieved message from RBMQ={}", msg);
		loanService.frontOfficeVerification(msg);
	}
}
