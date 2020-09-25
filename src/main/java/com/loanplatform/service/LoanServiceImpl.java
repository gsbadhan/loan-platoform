package com.loanplatform.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.loanplatform.common.AuthorityAction;
import com.loanplatform.common.LoanStage;
import com.loanplatform.common.LoanStatus;
import com.loanplatform.pojo.CarOfficeMessage;
import com.loanplatform.pojo.ConfirmationMessage;
import com.loanplatform.pojo.DisburseOfficeMessage;
import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.pojo.LoanAuthorityResponse;
import com.loanplatform.pojo.LoanFormRequest;
import com.loanplatform.pojo.RiskOfficeMessage;
import com.loanplatform.repository.LoanWorkflowPojo;
import com.loanplatform.repository.LoanWorkflowRepository;

import lombok.extern.slf4j.Slf4j;

@Service("LoanServiceImpl")
@Slf4j
public class LoanServiceImpl implements LoanService {

	@Autowired
	private Environment env;
	@Autowired
	private LoanWorkflowRepository loanWorkflowRepo;
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
		try {
			LoanWorkflowPojo newWorkflowPojo = new LoanWorkflowPojo();
			newWorkflowPojo.setFrontOffice(LoanStatus.PENDING.name());
			LoanWorkflowPojo workflowPojoDb = loanWorkflowRepo.save(newWorkflowPojo);
			FrontOfficeMessage msg = new FrontOfficeMessage(workflowPojoDb.getId().toString(),
					request.getCustomerDetail().getUid(), request.getCustomerDetail().getFirstName(),
					request.getCustomerDetail().getLastName(), request.getCarDetail());
			amqpTemplate.convertAndSend(env.getProperty("rabbitmq.exchnage.loanprocessing"),
					env.getProperty("rabbitmq.queue.frontoffice.verify.routingkey"), msg);
			log.info("sent message to RBMQ={}", msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validate(LoanFormRequest request) {
		// TODO Auto-generated method stub
	}

	@Override
	public void frontOfficeVerification(FrontOfficeMessage msg) {
		LoanAuthorityResponse response = frontOfficeAuthority.process(msg);
		updateWorkflowData(LoanStage.FRONT_OFFICE, response,
				loanWorkflowRepo.findById(Long.parseLong(msg.getLoanRequestId())).get());
		if (response.getAuthorityAction() == AuthorityAction.ACCEPTED) {
			CarOfficeMessage carOfficeMessage = new CarOfficeMessage(msg.getLoanRequestId(), msg.getUid(),
					msg.getFirstName(), msg.getLastName(), msg.getCarDetail());
			amqpTemplate.convertAndSend(env.getProperty("rabbitmq.exchnage.loanprocessing"),
					env.getProperty("rabbitmq.queue.cardept.verify.routingkey"), carOfficeMessage);
		} else {
			sendToNotificationQueue(new ConfirmationMessage(msg, response));
		}
	}

	@Override
	public void carOfficeVerification(CarOfficeMessage msg) {
		LoanAuthorityResponse response = carOfficeAuthority.process(msg);
		updateWorkflowData(LoanStage.CAR_LOAN_OFFICE, response,
				loanWorkflowRepo.findById(Long.parseLong(msg.getLoanRequestId())).get());
		if (response.getAuthorityAction() == AuthorityAction.ACCEPTED) {
			CarOfficeMessage carOfficeMessage = new CarOfficeMessage(msg.getLoanRequestId(), msg.getUid(),
					msg.getFirstName(), msg.getLastName(), msg.getCarDetail());
			amqpTemplate.convertAndSend(env.getProperty("rabbitmq.exchnage.loanprocessing"),
					env.getProperty("rabbitmq.queue.riskdept.verify.routingkey"), carOfficeMessage);
		} else {
			sendToNotificationQueue(new ConfirmationMessage(msg, response));
		}
	}

	@Override
	public void riskOfficeVerification(RiskOfficeMessage msg) {
		LoanAuthorityResponse response = riskOfficeAuthority.process(msg);
		updateWorkflowData(LoanStage.RISK_OFFICE, response,
				loanWorkflowRepo.findById(Long.parseLong(msg.getLoanRequestId())).get());
		if (response.getAuthorityAction() == AuthorityAction.ACCEPTED) {
			CarOfficeMessage carOfficeMessage = new CarOfficeMessage(msg.getLoanRequestId(), msg.getUid(),
					msg.getFirstName(), msg.getLastName(), msg.getCarDetail());
			amqpTemplate.convertAndSend(env.getProperty("rabbitmq.exchnage.loanprocessing"),
					env.getProperty("rabbitmq.queue.disbursaldept.verify.routingkey"), carOfficeMessage);
		} else {
			sendToNotificationQueue(new ConfirmationMessage(msg, response));
		}
	}

	@Override
	public void disbursalOfficeAction(DisburseOfficeMessage msg) {
		LoanAuthorityResponse response = disburseOfficeAuthority.process(msg);
		updateWorkflowData(LoanStage.DISBURSAL_OFFICE, response,
				loanWorkflowRepo.findById(Long.parseLong(msg.getLoanRequestId())).get());
		sendToNotificationQueue(new ConfirmationMessage(msg, response));
	}

	private void sendToNotificationQueue(ConfirmationMessage confirmationMessage) {
		amqpTemplate.convertAndSend(env.getProperty("rabbitmq.exchnage.loanprocessing"),
				env.getProperty("rabbitmq.queue.loanconfirmation.routingkey"), confirmationMessage);
	}

	private void updateWorkflowData(LoanStage stage, LoanAuthorityResponse response, LoanWorkflowPojo workflowPojoDb) {
		String status = response.getAuthorityAction() == AuthorityAction.ACCEPTED ? LoanStatus.ACCEPTED.name()
				: LoanStatus.REJECTED.name();
		switch (stage) {
		case FRONT_OFFICE:
			workflowPojoDb.setFrontOffice(status);
			break;
		case CAR_LOAN_OFFICE:
			workflowPojoDb.setCarloanOffice(status);
			break;
		case RISK_OFFICE:
			workflowPojoDb.setRiskOffice(status);
			break;
		case DISBURSAL_OFFICE:
			workflowPojoDb.setDisbursalOffice(status);
			break;
		default:
			break;
		}
		loanWorkflowRepo.save(workflowPojoDb);
	}
}
