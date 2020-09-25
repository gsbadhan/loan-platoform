package com.loanplatform.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.loanplatform.pojo.FrontOfficeMessage;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoanApprovalListener {

	@RabbitListener(queues = "frontOffice.verify.queue")
	public void recievedMessage(FrontOfficeMessage msg) {
		log.info("Recieved Message From RabbitMQ: " + msg);

	}
}
