package com.loanplatform.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loanplatform.common.AuthType;
import com.loanplatform.pojo.AuthRequest;
import com.loanplatform.pojo.AuthResponse;
import com.loanplatform.startup.BeanConfigs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BeanConfigs.class, AuthServiceImpl.class })
class AuthServiceImplTestIT {

	@Autowired
	@Qualifier("AuthServiceImpl")
	private AuthServiceImpl authServiceImpl;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testAuthorizePAN() {
		AuthType type = AuthType.PAN;
		String uid = "XDFRT568";
		Map<String, Object> meta = Collections.emptyMap();
		AuthRequest body = new AuthRequest(uid, meta);
		AuthResponse response = authServiceImpl.authorize(type, body);
		assertNotNull(response);
		assertNotNull(response.getAccessToken());
	}

	@Test
	void testAuthorizeADHAR() {
		AuthType type = AuthType.ADHAR;
		String uid = "5262738940";
		Map<String, Object> meta = Collections.emptyMap();
		AuthRequest body = new AuthRequest(uid, meta);
		AuthResponse response = authServiceImpl.authorize(type, body);
		assertNotNull(response);
		assertNotNull(response.getAccessToken());
	}

	@Test
	void testAuthorizeThrowException() {
		assertThrows(Exception.class, () -> {
			AuthType type = AuthType.ADHAR;
			String uid = "5262738940";
			Map<String, Object> meta = Collections.emptyMap();
			AuthRequest body = new AuthRequest(uid, meta);
			authServiceImpl.authorize(type, body);
		});

	}

}
