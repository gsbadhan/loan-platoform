package com.loanplatform.pojo;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = RiskOfficeMessage.class)
public class RiskOfficeMessage extends LoanApprovalMessage {
	private String loanRequestId;
	private String uid;
	private String firstName;
	private String lastName;
	private String email;
	private CarDetail carDetail;

}
