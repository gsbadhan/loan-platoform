package com.loanplatform.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.loanplatform.common.AuthType;
import com.loanplatform.common.Messages;
import com.loanplatform.pojo.AuthRequest;
import com.loanplatform.pojo.AuthResponse;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;

@Service("AuthServiceImpl")
@Slf4j
public class AuthServiceImpl implements AuthService {

	private RestTemplate authRestTemplate;
	private String pancardServiceUrl;
	private String adharCardServiceUrl;

	@Autowired
	public AuthServiceImpl(@Qualifier("authRestTemplate") RestTemplate authRestTemplate, Environment env) {
		pancardServiceUrl = env.getProperty("pancard.gw.url");
		adharCardServiceUrl = env.getProperty("adharcard.gw.url");
	}

	@Override
	public AuthResponse authorize(AuthType type, AuthRequest body) {
		AuthResponse response = null;
		switch (type) {
		case PAN:
			response = panCardAuthorisation(body);
			break;
		case ADHAR:
			response = adharCardAuthorisation(body);
			break;
		default:
			response = new AuthResponse();
			response.setStatus(false);
			Map<String, String> errors = new HashMap<String, String>(1);
			errors.put(Messages.NOT_SUPPORTED.name(), Messages.NOT_SUPPORTED.getValue());
			response.setErrors(errors);
			break;
		}
		return response;
	}

	@CircuitBreaker(fallbackMethod = "authGWFallback", name = "authGWCircuitBreaker")
	private AuthResponse panCardAuthorisation(AuthRequest body) {
		Map<String, String> request = new HashMap<String, String>();
		request.put("pan", body.getUid());
		log.info("panCardAuthorisation request {}", request);
		@SuppressWarnings("unchecked")
		Map<String, String> response = authRestTemplate.postForEntity(pancardServiceUrl, request, Map.class).getBody();
		log.info("panCardAuthorisation response {}", response);
		return new AuthResponse(response.get("pan-token"));
	}

	@CircuitBreaker(fallbackMethod = "authGWFallback", name = "authGWCircuitBreaker")
	private AuthResponse adharCardAuthorisation(AuthRequest body) {
		Map<String, String> request = new HashMap<String, String>();
		request.put("adhar", body.getUid());
		log.info("adharCardAuthorisation request {}", request);
		@SuppressWarnings("unchecked")
		Map<String, String> response = authRestTemplate.postForEntity(adharCardServiceUrl, request, Map.class)
				.getBody();
		log.info("adharCardAuthorisation response {}", response);
		return new AuthResponse(response.get("adhar-token"));
	}

	private AuthResponse authGWFallback(Throwable t) {
		log.error("error occured in authorize", t);
		AuthResponse response = new AuthResponse();
		response.setStatus(false);
		Map<String, String> errors = new HashMap<String, String>(1);
		errors.put(Messages.AUTH_SERVICE_DOWN.name(), Messages.AUTH_SERVICE_DOWN.getValue());
		response.setErrors(errors);
		return response;
	}

}
