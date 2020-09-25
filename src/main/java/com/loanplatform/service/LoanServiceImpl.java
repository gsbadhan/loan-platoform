package com.loanplatform.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.loanplatform.common.AuthorityAction;
import com.loanplatform.pojo.CarOfficeMessage;
import com.loanplatform.pojo.ConfirmationMessage;
import com.loanplatform.pojo.DisburseOfficeMessage;
import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.pojo.LoanAuthorityResponse;
import com.loanplatform.pojo.LoanFormRequest;
import com.loanplatform.pojo.RiskOfficeMessage;
import com.loanplatform.repository.LoanWorkflowRepo;

import lombok.extern.slf4j.Slf4j;

@Service("LoanServiceImpl")
@Slf4j
public class LoanServiceImpl implements LoanService {

	@Autowired
	private LoanWorkflowRepo loanWorkflowRepo;
	@Autowired
	private AmqpTemplate amqpTemplate;
	@Autowired
	@Qualifier("FrontOfficeAuthorityImpl")
	private LoanAuthority<FrontOfficeMessage> frontOfficeAuthority;
	@Autowired
	@Qualifier("CarOfficeAuthorityImpl")
	private LoanAuthority<CarOfficeMessage> carOfficeAuthority;
	@Autowired
	@Qualifier("RiskOfficeAuthorityImpl")
	private LoanAuthority<RiskOfficeMessage> riskOfficeAuthority;
	@Autowired
	@Qualifier("DisbursalOfficeAuthorityImpl")
	private LoanAuthority<DisburseOfficeMessage> disburseOfficeAuthority;

	@Override
	public void initiateLoanRequest(LoanFormRequest request) {
		validate(request);
		Long loanRequestId = loanWorkflowRepo.save(null);
		FrontOfficeMessage msg = new FrontOfficeMessage(loanRequestId.toString(), request.getCustomerDetail().getUid(),
				request.getCustomerDetail().getFirstName(), request.getCustomerDetail().getLastName(),
				request.getCarDetail());
		amqpTemplate.convertAndSend("loanProcessingExchng", "frontOffice.form.verify", msg);
		log.info("sent message to RBMQ={}", msg);
	}

	private void validate(LoanFormRequest request) {
		// TODO Auto-generated method stub

	}

	@Override
	public void frontOfficeVerification(FrontOfficeMessage msg) {
		LoanAuthorityResponse response = frontOfficeAuthority.process(msg);
		if (response.getAuthorityAction() == AuthorityAction.ACCEPTED) {
			CarOfficeMessage carOfficeMessage = new CarOfficeMessage(msg.getLoanRequestId(), msg.getUid(),
					msg.getFirstName(), msg.getLastName(), msg.getCarDetail());
			amqpTemplate.convertAndSend("loanProcessingExchng", "cardept.form.verify", carOfficeMessage);
		} else {
			sendToNotificationQueue(new ConfirmationMessage(msg, response));
		}
	}

	@Override
	public void carOfficeVerification(CarOfficeMessage msg) {
		LoanAuthorityResponse response = carOfficeAuthority.process(msg);
		if (response.getAuthorityAction() == AuthorityAction.ACCEPTED) {
			CarOfficeMessage carOfficeMessage = new CarOfficeMessage(msg.getLoanRequestId(), msg.getUid(),
					msg.getFirstName(), msg.getLastName(), msg.getCarDetail());
			amqpTemplate.convertAndSend("loanProcessingExchng", "riskdept.form.verify", carOfficeMessage);
		} else {
			sendToNotificationQueue(new ConfirmationMessage(msg, response));
		}
	}

	@Override
	public void riskOfficeVerification(RiskOfficeMessage msg) {
		LoanAuthorityResponse response = riskOfficeAuthority.process(msg);
		if (response.getAuthorityAction() == AuthorityAction.ACCEPTED) {
			CarOfficeMessage carOfficeMessage = new CarOfficeMessage(msg.getLoanRequestId(), msg.getUid(),
					msg.getFirstName(), msg.getLastName(), msg.getCarDetail());
			amqpTemplate.convertAndSend("loanProcessingExchng", "disbursaldept.form.verify", carOfficeMessage);
		} else {
			sendToNotificationQueue(new ConfirmationMessage(msg, response));
		}
	}

	@Override
	public void disbursalOfficeAction(DisburseOfficeMessage msg) {
		LoanAuthorityResponse response = disburseOfficeAuthority.process(msg);
		sendToNotificationQueue(new ConfirmationMessage(msg, response));
	}

	private void sendToNotificationQueue(ConfirmationMessage confirmationMessage) {
		amqpTemplate.convertAndSend("loanProcessingExchng", "loanconfirmation.notify", confirmationMessage);
	}
}
