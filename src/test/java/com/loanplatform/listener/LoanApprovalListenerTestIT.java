package com.loanplatform.listener;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loanplatform.common.CarBodyType;
import com.loanplatform.common.FuelType;
import com.loanplatform.common.LoanStage;
import com.loanplatform.common.LoanStatus;
import com.loanplatform.pojo.CarDetail;
import com.loanplatform.pojo.CarOfficeMessage;
import com.loanplatform.pojo.ConfirmationMessage;
import com.loanplatform.pojo.DisburseOfficeMessage;
import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.pojo.LoanApprovalMessage;
import com.loanplatform.pojo.RiskOfficeMessage;
import com.loanplatform.startup.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
class LoanApprovalListenerTestIT {

	@Autowired
	private LoanApprovalListener loanApprovalListener;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFrontOfficeRecievedMessage() {
		assertDoesNotThrow(() -> {
			Long loanRequestId = 10L;
			String uid = "BRTUSFH78";
			String firstName = "ravi";
			String lastName = "jam";
			String email = "abc@mail.com";
			CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
					"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
			FrontOfficeMessage msg = new FrontOfficeMessage(loanRequestId.toString(), uid, firstName, lastName, email,
					carDetail);
			loanApprovalListener.frontOfficeRecievedMessage(msg);
		});
	}

	@Test
	void testCarOfficeRecievedMessage() {
		assertDoesNotThrow(() -> {
			Long loanRequestId = 10L;
			String uid = "BRTUSFH78";
			String firstName = "ravi";
			String lastName = "jam";
			String email = "abc@mail.com";
			CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
					"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
			CarOfficeMessage msg = new CarOfficeMessage(loanRequestId.toString(), uid, firstName, lastName, email,
					carDetail);
			loanApprovalListener.carOfficeRecievedMessage(msg);
		});
	}

	@Test
	void testRiskOfficeRecievedMessage() {
		assertDoesNotThrow(() -> {
			Long loanRequestId = 10L;
			String uid = "BRTUSFH78";
			String firstName = "ravi";
			String lastName = "jam";
			String email = "abc@mail.com";
			CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
					"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
			RiskOfficeMessage msg = new RiskOfficeMessage(loanRequestId.toString(), uid, firstName, lastName, email,
					carDetail);
			loanApprovalListener.riskOfficeRecievedMessage(msg);
		});
	}

	@Test
	void testDisbursalOfficeRecievedMessage() {
		assertDoesNotThrow(() -> {
			Long loanRequestId = 10L;
			String uid = "BRTUSFH78";
			String firstName = "ravi";
			String lastName = "jam";
			String email = "abc@mail.com";
			CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
					"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
			DisburseOfficeMessage msg = new DisburseOfficeMessage(loanRequestId.toString(), uid, firstName, lastName,
					email, carDetail);
			loanApprovalListener.disbursalOfficeRecievedMessage(msg);
		});
	}

	@Test
	void testLoanConfirmationRecievedMessage() {
		assertDoesNotThrow(() -> {
			LoanApprovalMessage approvalMessage = new LoanApprovalMessage();
			approvalMessage.setLoanStage(LoanStage.DISBURSAL_OFFICE);
			approvalMessage.setLoanStatus(LoanStatus.ACCEPTED);
			ConfirmationMessage msg = new ConfirmationMessage(approvalMessage);
			loanApprovalListener.loanConfirmationRecievedMessage(msg);
		});
	}

}
