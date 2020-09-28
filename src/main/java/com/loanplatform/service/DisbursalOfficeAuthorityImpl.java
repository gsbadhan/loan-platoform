package com.loanplatform.service;

import org.springframework.stereotype.Component;

import com.loanplatform.pojo.DisburseOfficeMessage;
import com.loanplatform.pojo.LoanAuthorityResponse;

/**
 * 
 * Disburse office business logic handler
 */
@Component("DisbursalOfficeAuthorityImpl")
public class DisbursalOfficeAuthorityImpl implements LoanAuthority<DisburseOfficeMessage> {

	@Override
	public LoanAuthorityResponse process(DisburseOfficeMessage request) {
		// TODO: build business logic based on requirements
		return new LoanAuthorityResponse();
	}

}
