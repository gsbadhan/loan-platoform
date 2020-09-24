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

@RestController
@RequestMapping("/v1/auth")
public class AuthController {

	@Autowired
	@Qualifier("AuthServiceImpl")
	private AuthService authService;

	@PostMapping("/{type}/verify")
	public ResponseEntity<?> verification(@PathVariable(value = "type") AuthType type, @RequestBody AuthRequest body) {
		AuthResponse response = authService.authorize(type, body);
		return HttpResponse.apiAuthResponse(response);
	}
}
