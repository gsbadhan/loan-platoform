package com.loanplatform.service;

import org.springframework.stereotype.Component;

import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.pojo.LoanAuthorityResponse;

/**
 * 
 * Front office business logic handler
 */
@Component("FrontOfficeAuthorityImpl")
public class FrontOfficeAuthorityImpl implements LoanAuthority<FrontOfficeMessage> {

	@Override
	public LoanAuthorityResponse process(FrontOfficeMessage request) {
		// TODO: build business logic based on requirements
		return new LoanAuthorityResponse();
	}

}
