package com.loanplatform.service;

import com.loanplatform.common.AuthType;
import com.loanplatform.pojo.AuthRequest;
import com.loanplatform.pojo.AuthResponse;

public interface AuthService {

	AuthResponse authorize(AuthType type, AuthRequest body);

}
