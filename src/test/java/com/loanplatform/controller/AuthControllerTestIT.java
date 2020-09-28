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
import com.loanplatform.common.AuthType;
import com.loanplatform.pojo.AuthRequest;
import com.loanplatform.startup.Application;

@SpringBootTest(classes = { Application.class })
@AutoConfigureMockMvc
class AuthControllerTestIT {

	@Autowired
	private MockMvc mockMvc;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testVerificationOk() throws Exception {
		AuthRequest body = new AuthRequest();
		String PAN = "BFGTRUO89";
		body.setUid(PAN);
		String type = AuthType.PAN.name();
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/{type}/verify", type).contentType("application/json")
				.content(new Gson().toJson(body))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.accessToken").exists());
	}

	@Test
	void testVerificationNotSupported() throws Exception {
		AuthRequest body = new AuthRequest();
		String PAN = "BFGTRUO89";
		body.setUid(PAN);
		String type = "XYZ";
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/{type}/verify", type).contentType("application/json")
				.content(new Gson().toJson(body))).andExpect(MockMvcResultMatchers.status().isNotAcceptable())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.NOT_SUPPORTED").exists());
	}

	@Test
	void testVerificationAuthNotWorking() throws Exception {
		AuthRequest body = new AuthRequest();
		String PAN = "BFGTRUO89";
		body.setUid(PAN);
		String type = AuthType.PAN.name();
		mockMvc.perform(MockMvcRequestBuilders.post("/v1/auth/{type}/verify", type).contentType("application/json")
				.content(new Gson().toJson(body))).andExpect(MockMvcResultMatchers.status().isInternalServerError())
				.andExpect(MockMvcResultMatchers.jsonPath("$.errors.SERVER_ERROR").exists());
	}

}
