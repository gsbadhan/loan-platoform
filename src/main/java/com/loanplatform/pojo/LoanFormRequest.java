package com.loanplatform.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoanFormRequest {
	private String uid;
	private String mobileNumber;
	private String email;
	private String firstName;
	private String lastName;
	private Float loanAmount;
	private CarDetail carDetail;

}
