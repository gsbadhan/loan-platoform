package com.loanplatform.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.loanplatform.pojo.CarOfficeMessage;
import com.loanplatform.pojo.DisburseOfficeMessage;
import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.pojo.RiskOfficeMessage;
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
		log.info("frontOfficeRecievedMessage recieved message from RBMQ={}", msg);
		loanService.frontOfficeVerification(msg);
	}

	@RabbitListener(queues = "cardept.verify.queue")
	public void carOfficeRecievedMessage(CarOfficeMessage msg) {
		log.info("carOfficeRecievedMessage recieved message from RBMQ={}", msg);
		loanService.carOfficeVerification(msg);
	}

	@RabbitListener(queues = "riskdept.verify.queue")
	public void riskOfficeRecievedMessage(RiskOfficeMessage msg) {
		log.info("riskOfficeRecievedMessage recieved message from RBMQ={}", msg);
		loanService.riskOfficeVerification(msg);
	}

	@RabbitListener(queues = "disbursaldept.verify.queue")
	public void disbursalOfficeRecievedMessage(DisburseOfficeMessage msg) {
		log.info("disbursalOfficeRecievedMessage recieved message from RBMQ={}", msg);
		loanService.disbursalOfficeAction(msg);
	}

	@RabbitListener(queues = "loanconfirmation.queue")
	public void loanConfirmationRecievedMessage(RiskOfficeMessage msg) {
		log.info("loanConfirmationRecievedMessage recieved message from RBMQ={}", msg);
	}
}
