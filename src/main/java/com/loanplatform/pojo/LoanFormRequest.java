package com.loanplatform.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanFormRequest {
	private Float loanAmount;
	private CustomerDetail customerDetail;
	private CarDetail carDetail;

}
