package com.loanplatform.startup;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange("deadLoanProcessingExchng");
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange("loanProcessingExchng");
	}

	@Bean
	Queue deadLoanQueue() {
		return QueueBuilder.durable("deadloan.queue").build();
	}

	@Bean
	Binding deadLoanQueueBinding() {
		return BindingBuilder.bind(deadLoanQueue()).to(deadLetterExchange()).with("deadLoan");
	}

	@Bean
	Queue frontOfficeLoanVerificationQueue() {
		return QueueBuilder.durable("frontOffice.verify.queue")
				.withArgument("x-dead-letter-exchange", "deadLoanProcessingExchng")
				.withArgument("x-dead-letter-routing-key", "deadLoan").build();
	}

	@Bean
	Binding frontOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(frontOfficeLoanVerificationQueue()).to(exchange()).with("frontOffice.form.verify");
	}

	@Bean
	Queue carOfficeLoanVerificationQueue() {
		return QueueBuilder.durable("cardept.verify.queue")
				.withArgument("x-dead-letter-exchange", "deadLoanProcessingExchng")
				.withArgument("x-dead-letter-routing-key", "deadLoan").build();
	}

	@Bean
	Binding carOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(carOfficeLoanVerificationQueue()).to(exchange()).with("cardept.form.verify");
	}

	@Bean
	Queue riskOfficeLoanVerificationQueue() {
		return QueueBuilder.durable("riskdept.verify.queue")
				.withArgument("x-dead-letter-exchange", "deadLoanProcessingExchng")
				.withArgument("x-dead-letter-routing-key", "deadLoan").build();
	}

	@Bean
	Binding riskOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(riskOfficeLoanVerificationQueue()).to(exchange()).with("riskdept.form.verify");
	}

	@Bean
	Queue disbursalOfficeLoanVerificationQueue() {
		return QueueBuilder.durable("disbursaldept.verify.queue")
				.withArgument("x-dead-letter-exchange", "deadLoanProcessingExchng")
				.withArgument("x-dead-letter-routing-key", "deadLoan").build();
	}

	@Bean
	Binding disbursalOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(disbursalOfficeLoanVerificationQueue()).to(exchange())
				.with("disbursaldept.form.verify");
	}

	@Bean
	Queue loanConfirmationQueue() {
		return QueueBuilder.durable("loanconfirmation.queue")
				.withArgument("x-dead-letter-exchange", "deadLoanProcessingExchng")
				.withArgument("x-dead-letter-routing-key", "deadLoan").build();
	}

	@Bean
	Binding loanConfrimationQueueBinding() {
		return BindingBuilder.bind(loanConfirmationQueue()).to(exchange()).with("loanconfirmation.notify");
	}

	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}

	public AmqpTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
}
