package com.loanplatform.startup;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.extern.slf4j.Slf4j;

/**
 * Application Startup class
 *
 */

@SpringBootApplication
@ComponentScan(basePackages = { "com.*" })
@EnableJpaRepositories(basePackages = { "com.loanplatform.repository" })
@EntityScan(basePackages = { "com.loanplatform.repository" })
@EnableTransactionManagement
@Slf4j
public class Application {

	public static void main(String[] args) {
		ApplicationContext ctx = SpringApplication.run(Application.class, args);
		log.info("loanplatform started..{}", ctx);
	}
}
