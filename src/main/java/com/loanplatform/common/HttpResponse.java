package com.loanplatform.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.loanplatform.pojo.AuthResponse;

public class HttpResponse {
	private HttpResponse() {
	}

	public static ResponseEntity<?> apiAuthResponse(AuthResponse value) {
		if (value.getStatus()) {
			return ResponseEntity.status(HttpStatus.OK).body(value);
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(value);
		}
	}
}
