package com.loanplatform.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class LoanStatusResponse extends GenericResponse {
	private String frontOffice;
	private String carLoanOffice;
	private String riskOffice;
	private String disbursalOffice;

}
