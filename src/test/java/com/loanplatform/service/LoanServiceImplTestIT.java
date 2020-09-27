package com.loanplatform.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loanplatform.common.CarBodyType;
import com.loanplatform.common.FuelType;
import com.loanplatform.pojo.CarDetail;
import com.loanplatform.pojo.CarOfficeMessage;
import com.loanplatform.pojo.CustomerDetail;
import com.loanplatform.pojo.DisburseOfficeMessage;
import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.pojo.LoanFormRequest;
import com.loanplatform.pojo.LoanStatusResponse;
import com.loanplatform.pojo.LoanSubmitResponse;
import com.loanplatform.pojo.RiskOfficeMessage;
import com.loanplatform.startup.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { Application.class })
class LoanServiceImplTestIT {

	@Autowired
	@Qualifier("LoanServiceImpl")
	private LoanServiceImpl loanServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testInitiateLoanRequest() {
		float loanAmount = 154627F;
		CustomerDetail customerDetail = new CustomerDetail("BRTUSFH78", "445234789", "abc@mail.com", "ravi", "jam");
		CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
				"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
		LoanFormRequest request = new LoanFormRequest(loanAmount, customerDetail, carDetail);
		LoanSubmitResponse response = loanServiceImpl.initiateLoanRequest(request);
		assertNotNull(response);
		assertNotNull(response.getLoanRequestId());
	}

	@Test
	void testLoanStatus() {
		Long loanRequestId = 10L;
		LoanStatusResponse response = loanServiceImpl.loanStatus(loanRequestId);
		assertNotNull(response);
		assertTrue(response.getStatus());
	}

	@Test
	void testFrontOfficeVerification() {
		Long loanRequestId = 10L;
		String uid = "BRTUSFH78";
		String firstName = "ravi";
		String lastName = "jam";
		String email = "abc@mail.com";
		CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
				"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
		FrontOfficeMessage msg = new FrontOfficeMessage(loanRequestId.toString(), uid, firstName, lastName, email,
				carDetail);
		loanServiceImpl.frontOfficeVerification(msg);
	}

	@Test
	void testCarOfficeVerification() {
		Long loanRequestId = 10L;
		String uid = "BRTUSFH78";
		String firstName = "ravi";
		String lastName = "jam";
		String email = "abc@mail.com";
		CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
				"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
		CarOfficeMessage msg = new CarOfficeMessage(loanRequestId.toString(), uid, firstName, lastName, email,
				carDetail);
		loanServiceImpl.carOfficeVerification(msg);
	}

	@Test
	void testRiskOfficeVerification() {
		Long loanRequestId = 10L;
		String uid = "BRTUSFH78";
		String firstName = "ravi";
		String lastName = "jam";
		String email = "abc@mail.com";
		CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
				"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
		RiskOfficeMessage msg = new RiskOfficeMessage(loanRequestId.toString(), uid, firstName, lastName, email,
				carDetail);
		loanServiceImpl.riskOfficeVerification(msg);
	}

	@Test
	void testDisbursalOfficeAction() {
		Long loanRequestId = 10L;
		String uid = "BRTUSFH78";
		String firstName = "ravi";
		String lastName = "jam";
		String email = "abc@mail.com";
		CarDetail carDetail = new CarDetail("DLAQGH-4526", "delhi", "DF-GH-3578", "CHAS-67283849", "EN675", "IVTC",
				"1600", "9956453201", CarBodyType.SEADN, FuelType.PETROL, 4, new Date(6373748), new Date(6373889));
		DisburseOfficeMessage msg = new DisburseOfficeMessage(loanRequestId.toString(), uid, firstName, lastName, email,
				carDetail);
		loanServiceImpl.disbursalOfficeAction(msg);
	}

}
