package com.loanplatform.repository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.loanplatform.common.LoanStatus;
import com.loanplatform.startup.BeanConfigs;

@DataJpaTest
@AutoConfigurationPackage
@ContextConfiguration(classes = { BeanConfigs.class }, initializers = {
		LoanWorkflowRepositoryTestTestContainersIT.Initializer.class })
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class LoanWorkflowRepositoryTestTestContainersIT {
	@SuppressWarnings("rawtypes")
	@Container
	private static MySQLContainer mysqlDB = new MySQLContainer();

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
			TestPropertyValues
					.of("spring.datasource.url=" + mysqlDB.getJdbcUrl(),
							"spring.datasource.username=" + mysqlDB.getUsername(),
							"spring.datasource.password=" + mysqlDB.getPassword())
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}

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
