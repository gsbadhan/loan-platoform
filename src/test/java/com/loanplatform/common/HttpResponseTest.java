package com.loanplatform.common;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.loanplatform.pojo.GenericResponse;

class HttpResponseTest {

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testApiResponseOK() {
		GenericResponse input = new GenericResponse(true, Collections.emptyMap());
		ResponseEntity<?> responseEntity = HttpResponse.ApiResponse(input);
		assertTrue(responseEntity.getStatusCode() == HttpStatus.OK);
	}

	@Test
	void testApiResponseNOT_FOUND() {
		GenericResponse input = new GenericResponse();
		input.setStatus(false);
		input.appendErrors(Messages.NOT_FOUND.name(), Messages.NOT_FOUND.getValue());
		ResponseEntity<?> responseEntity = HttpResponse.ApiResponse(input);
		assertTrue(responseEntity.getStatusCode() == HttpStatus.NOT_FOUND);
	}

	@Test
	void testApiResponseSERVER_ERROR() {
		GenericResponse input = new GenericResponse();
		input.setStatus(false);
		input.appendErrors(Messages.SERVER_ERROR.name(), Messages.SERVER_ERROR.getValue());
		ResponseEntity<?> responseEntity = HttpResponse.ApiResponse(input);
		assertTrue(responseEntity.getStatusCode() == HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Test
	void testApiResponseUNAUTHORIZED() {
		GenericResponse input = new GenericResponse();
		input.setStatus(false);
		input.appendErrors(Messages.UNAUTHORIZED.name(), Messages.UNAUTHORIZED.getValue());
		ResponseEntity<?> responseEntity = HttpResponse.ApiResponse(input);
		assertTrue(responseEntity.getStatusCode() == HttpStatus.UNAUTHORIZED);
	}

	@Test
	void testApiResponseNOT_SUPPORTED() {
		GenericResponse input = new GenericResponse();
		input.setStatus(false);
		input.appendErrors(Messages.NOT_SUPPORTED.name(), Messages.NOT_SUPPORTED.getValue());
		ResponseEntity<?> responseEntity = HttpResponse.ApiResponse(input);
		assertTrue(responseEntity.getStatusCode() == HttpStatus.NOT_ACCEPTABLE);
	}

	@Test
	void testApiResponseNOT_IMPLEMENTED() {
		GenericResponse input = new GenericResponse();
		input.setStatus(false);
		ResponseEntity<?> responseEntity = HttpResponse.ApiResponse(input);
		assertTrue(responseEntity.getStatusCode() == HttpStatus.NOT_IMPLEMENTED);
	}

}
