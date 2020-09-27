package com.loanplatform.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.loanplatform.common.LoanStatus;
import com.loanplatform.startup.Application;
import com.loanplatform.startup.BeanConfigs;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { BeanConfigs.class, Application.class })
class LoanWorkflowRepositoryTestIT {
	@Autowired
	private LoanWorkflowRepository loanWorkflowRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testSave() {
		LoanWorkflowPojo newLoanWorkFlowPojo = new LoanWorkflowPojo(null, LoanStatus.PENDING.name(), null, null, null,
				null);
		LoanWorkflowPojo loanWorkflowDb = loanWorkflowRepository.save(newLoanWorkFlowPojo);
		assertNotNull(loanWorkflowDb);
		assertNotNull(loanWorkflowDb.getId());
		assertTrue(loanWorkflowDb.getFrontOffice().equals(LoanStatus.PENDING.name()));
	}

	@Test
	void testUpdate() {
		Long loanRequestId = 8L;
		LoanWorkflowPojo newLoanWorkFlowPojo = new LoanWorkflowPojo(loanRequestId, LoanStatus.ACCEPTED.name(), null,
				null, null, null);
		LoanWorkflowPojo loanWorkflowDb = loanWorkflowRepository.save(newLoanWorkFlowPojo);
		assertNotNull(loanWorkflowDb);
		assertNotNull(loanWorkflowDb.getId() == loanRequestId);
		assertTrue(loanWorkflowDb.getFrontOffice().equals(LoanStatus.ACCEPTED.name()));
	}

	@Test
	void testFoundById() {
		Long loanRequestId = 8L;
		Optional<LoanWorkflowPojo> data = loanWorkflowRepository.findById(loanRequestId);
		assertTrue(data.isPresent());
		LoanWorkflowPojo loanWorkflowDb = data.get();
		assertNotNull(loanWorkflowDb.getId() == loanRequestId);
	}

	@Test
	void testNotFouundById() {
		Long loanRequestId = 8L;
		Optional<LoanWorkflowPojo> data = loanWorkflowRepository.findById(loanRequestId);
		assertTrue(data.isEmpty());
	}

}
