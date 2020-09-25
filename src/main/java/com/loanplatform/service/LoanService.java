package com.loanplatform.service;

import com.loanplatform.pojo.CarOfficeMessage;
import com.loanplatform.pojo.DisburseOfficeMessage;
import com.loanplatform.pojo.FrontOfficeMessage;
import com.loanplatform.pojo.LoanFormRequest;
import com.loanplatform.pojo.RiskOfficeMessage;

public interface LoanService {

	void initiateLoanRequest(LoanFormRequest request);

	void frontOfficeVerification(FrontOfficeMessage msg);

	void carOfficeVerification(CarOfficeMessage msg);

	void riskOfficeVerification(RiskOfficeMessage msg);

	void disbursalOfficeAction(DisburseOfficeMessage msg);

}
