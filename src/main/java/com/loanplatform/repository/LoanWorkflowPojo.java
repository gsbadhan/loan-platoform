package com.loanplatform.repository;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "loan_workflow")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanWorkflowPojo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "front_office")
	private String frontOffice;

	@Column(name = "carloan_office")
	private String carloanOffice;

	@Column(name = "risk_office")
	private String riskOffice;

	@Column(name = "disbursal_office")
	private String disbursalOffice;

	@Column(name = "update_at")
	private Timestamp updateAt;
}
