package com.loanplatform.service;

import com.loanplatform.pojo.LoanAuthorityResponse;

/**
 * 
 * common interface for departmental actions
 */
public interface LoanAuthority<R> {
	/**
	 * Return approval status (ACCEPTED, REJECTED) after all checks & validation
	 * 
	 * @param request
	 * @return LoanAuthorityResponse
	 */
	LoanAuthorityResponse process(R request);
}
