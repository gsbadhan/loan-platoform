package com.loanplatform.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanplatform.pojo.LoanFormRequest;

@RestController
@RequestMapping("/v1/loan")
public class LoanController {

	@PostMapping("/submit")
	public ResponseEntity<?> loanSubmit(@RequestBody LoanFormRequest body) {
		return null;
	}

	@GetMapping("/status")
	public ResponseEntity<?> loanStatus() {
		return null;
	}
}
