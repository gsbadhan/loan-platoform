package com.loanplatform.pojo;

import java.util.List;

import com.loanplatform.common.LoanStage;
import com.loanplatform.common.LoanStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanApprovalMessage {
	private LoanStage loanStage;
	private LoanStatus loanStatus;
	private List<String> reviews;
}
