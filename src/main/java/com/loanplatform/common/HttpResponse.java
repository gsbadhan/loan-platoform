package com.loanplatform.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.loanplatform.pojo.GenericResponse;

public class HttpResponse {
	private HttpResponse() {
	}

	public static ResponseEntity<?> ApiResponse(GenericResponse response) {
		if (response.getStatus())
			return ResponseEntity.status(HttpStatus.OK).body(response);

		if (response.getErrors().containsKey(Messages.NOT_FOUND.name())) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
		} else if (response.getErrors().containsKey(Messages.SERVER_ERROR.name())) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		} else if (response.getErrors().containsKey(Messages.UNAUTHORIZED.name())) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
		}
		return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(response);
	}
}
