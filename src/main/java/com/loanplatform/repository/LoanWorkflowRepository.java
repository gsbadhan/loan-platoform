package com.loanplatform.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanWorkflowRepository extends JpaRepository<LoanWorkflowPojo, Long> {
}
