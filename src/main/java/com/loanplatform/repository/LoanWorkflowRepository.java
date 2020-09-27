package com.loanplatform.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanWorkflowRepository extends CrudRepository<LoanWorkflowPojo, Long> {
}
