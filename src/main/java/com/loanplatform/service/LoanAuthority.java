package com.loanplatform.service;

import com.loanplatform.pojo.LoanAuthorityResponse;

public interface LoanAuthority<R> {
	LoanAuthorityResponse process(R request);
}
