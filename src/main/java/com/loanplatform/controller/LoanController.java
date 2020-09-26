package com.loanplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanplatform.common.HttpResponse;
import com.loanplatform.pojo.LoanFormRequest;
import com.loanplatform.pojo.LoanStatusResponse;
import com.loanplatform.pojo.LoanSubmitResponse;
import com.loanplatform.service.LoanService;

@RestController
@RequestMapping("/v1/loan")
public class LoanController {

	@Autowired
	@Qualifier("LoanServiceImpl")
	private LoanService loanService;

	@PostMapping("/submit")
	public ResponseEntity<?> loanSubmit(@RequestBody LoanFormRequest body) {
		LoanSubmitResponse response = loanService.initiateLoanRequest(body);
		return HttpResponse.ApiResponse(response);
	}

	@GetMapping("/{loanRequestId}/status")
	public ResponseEntity<?> loanStatus(@PathVariable("loanRequestId") Long loanRequestId) {
		LoanStatusResponse response = loanService.loanStatus(loanRequestId);
		return HttpResponse.ApiResponse(response);
	}
}
