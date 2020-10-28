package com.loanplatform.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.loanplatform.common.AuthType;
import com.loanplatform.common.HttpResponse;
import com.loanplatform.pojo.AuthRequest;
import com.loanplatform.pojo.AuthResponse;
import com.loanplatform.service.AuthService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/v1/auth")
@Api(value = "Authorisation API service")
public class AuthController {

	enum test {
		XX;
	}

	@Autowired
	@Qualifier("AuthServiceImpl")
	private AuthService authService;

	@PostMapping("/{type}/verify")
	@ApiOperation(value = "Verify user identity based on PAN,ADHAR card")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Return auth token"),
			@ApiResponse(response = String.class, code = 400, message = "Bad request"),
			@ApiResponse(response = String.class, code = 401, message = "Unautorized"),
			@ApiResponse(response = String.class, code = 406, message = "Functionality not supported"),
			@ApiResponse(response = String.class, code = 500, message = "Getting error from server") })
	public ResponseEntity<?> verification(@PathVariable(value = "type") AuthType type, @RequestBody AuthRequest body) {
		AuthResponse response = authService.authorize(type, body);
		return HttpResponse.ApiResponse(response);
	}
}
