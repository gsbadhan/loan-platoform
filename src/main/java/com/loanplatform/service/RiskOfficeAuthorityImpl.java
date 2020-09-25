package com.loanplatform.service;

import org.springframework.stereotype.Component;

import com.loanplatform.pojo.LoanAuthorityResponse;
import com.loanplatform.pojo.RiskOfficeMessage;

@Component("RiskOfficeAuthorityImpl")
public class RiskOfficeAuthorityImpl implements LoanAuthority<RiskOfficeMessage> {

	@Override
	public LoanAuthorityResponse process(RiskOfficeMessage request) {
		// TODO: build business logic based on requirements
		return new LoanAuthorityResponse();
	}

}
