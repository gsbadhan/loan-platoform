package com.loanplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanplatform.pojo.LoanFormRequest;
import com.loanplatform.service.LoanService;

@RestController
@RequestMapping("/v1/loan")
public class LoanController {

	@Autowired
	@Qualifier("LoanServiceImpl")
	private LoanService loanService;

	@PostMapping("/submit")
	public ResponseEntity<?> loanSubmit(@RequestBody LoanFormRequest body) {
		loanService.initiateLoanRequest(body);
		return ResponseEntity.status(HttpStatus.OK).body(null);
	}

	@GetMapping("/status")
	public ResponseEntity<?> loanStatus() {
		return null;
	}
}
