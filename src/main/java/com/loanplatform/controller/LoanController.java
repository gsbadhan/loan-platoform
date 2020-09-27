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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/loan")
@Api(value = "Car loan API service")
public class LoanController {

	@Autowired
	@Qualifier("LoanServiceImpl")
	private LoanService loanService;

	@PostMapping("/submit")
	@ApiOperation(value = "Loan request initiation")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return loan ID"),
			@ApiResponse(response = String.class, code = 400, message = "Bad request"),
			@ApiResponse(response = String.class, code = 500, message = "Getting error from server") })
	public ResponseEntity<?> loanSubmit(@RequestBody LoanFormRequest body) {
		LoanSubmitResponse response = loanService.initiateLoanRequest(body);
		return HttpResponse.ApiResponse(response);
	}

	@GetMapping("/{loanRequestId}/status")
	@ApiOperation(value = "Track loan request")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return auth token"),
			@ApiResponse(response = String.class, code = 400, message = "Bad request"),
			@ApiResponse(response = String.class, code = 404, message = "Loan ID not exist"),
			@ApiResponse(response = String.class, code = 500, message = "Getting error from server") })
	public ResponseEntity<?> loanStatus(@PathVariable("loanRequestId") Long loanRequestId) {
		LoanStatusResponse response = loanService.loanStatus(loanRequestId);
		return HttpResponse.ApiResponse(response);
	}
}
