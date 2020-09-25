package com.loanplatform.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.loanplatform.common.LoanStage;
import com.loanplatform.common.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class, property = "@id", scope = LoanApprovalMessage.class)
public class LoanApprovalMessage {
	private LoanStage loanStage = LoanStage.FRONT_OFFICE;
	private LoanStatus loanStatus = LoanStatus.PENDING;
	private List<String> reviews;
}
