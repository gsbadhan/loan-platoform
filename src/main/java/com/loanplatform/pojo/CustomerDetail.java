package com.loanplatform.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetail {
	private String uid;
	private String mobileNumber;
	private String email;
	private String firstName;
	private String lastName;
}
