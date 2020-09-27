package com.loanplatform.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.loanplatform.pojo.CarDetail;
import com.loanplatform.pojo.CustomerDetail;
import com.loanplatform.pojo.LoanFormRequest;
import com.loanplatform.startup.Application;

@SpringBootTest(classes = { Application.class })
@AutoConfigureMockMvc
class LoanControllerTestIT {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLoanSubmitOk() throws Exception {
		CustomerDetail customerDetail = new CustomerDetail("BRTUSFH78", "445234789", "abc@mail.com", "ravi", "jam");
		CarDetail carDetail = new CarDetail();
		float loanAmount = 120000;
		LoanFormRequest body = new LoanFormRequest(loanAmount, customerDetail, carDetail);
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/loan/submit").contentType("application/json")
				.content(new Gson().toJson(body))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.loanRequestId").exists());
	}

	@Test
	void testLoanSubmitNotWorking() throws Exception {
		CustomerDetail customerDetail = new CustomerDetail("BRTUSFH78", "445234789", "abc@mail.com", "ravi", "jam");
		CarDetail carDetail = new CarDetail();
		float loanAmount = 120000;
		LoanFormRequest body = new LoanFormRequest(loanAmount, customerDetail, carDetail);
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/loan/submit").contentType("application/json")
				.content(new Gson().toJson(body))).andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.SERVER_ERROR").exists());
	}

	@Test
	void testLoanStatusOk() throws Exception {
		Long loanRequestId = 6L;
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/loan/{loanRequestId}/status", loanRequestId)
				.contentType("application/json")).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.frontOffice").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.carLoanOffice").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.riskOffice").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.disbursalOffice").exists());
	}

	@Test
	void testLoanStatusNotFound() throws Exception {
		Long loanRequestId = 1001L;
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/loan/{loanRequestId}/status", loanRequestId)
				.contentType("application/json")).andExpect(MockMvcResultMatchers.status().isNotFound())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.NOT_FOUND").exists());
	}

	@Test
	void testLoanStatusNotWorking() throws Exception {
		Long loanRequestId = 1001L;
		mockMvc.perform(MockMvcRequestBuilders.get("/v1/loan/{loanRequestId}/status", loanRequestId)
				.contentType("application/json")).andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.SERVER_ERROR").exists());
	}

}
