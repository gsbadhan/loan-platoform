package com.loanplatform.service;

import org.springframework.stereotype.Component;

import com.loanplatform.pojo.CarOfficeMessage;
import com.loanplatform.pojo.LoanAuthorityResponse;

@Component("CarOfficeAuthorityImpl")
public class CarOfficeAuthorityImpl implements LoanAuthority<CarOfficeMessage> {

	@Override
	public LoanAuthorityResponse process(CarOfficeMessage request) {
		// TODO: build business logic based on requirements
		return new LoanAuthorityResponse();
	}

}
