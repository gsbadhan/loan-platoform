package com.loanplatform.pojo;

import java.util.List;

import com.loanplatform.common.AuthorityAction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanAuthorityResponse {
	private AuthorityAction authorityAction = AuthorityAction.ACCEPTED;
	private List<String> reviews;
}
