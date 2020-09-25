package com.loanplatform.startup;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitMQConfig {

	@Autowired
	private Environment env;

	@Bean
	DirectExchange deadLetterExchange() {
		return new DirectExchange(env.getProperty("rabbitmq.exchnage.deadloanprocessing"));
	}

	@Bean
	DirectExchange exchange() {
		return new DirectExchange(env.getProperty("rabbitmq.exchnage.loanprocessing"));
	}

	@Bean
	Queue deadLoanQueue() {
		return QueueBuilder.durable(env.getProperty("rabbitmq.queue.deadloan")).build();
	}

	@Bean
	Binding deadLoanQueueBinding() {
		return BindingBuilder.bind(deadLoanQueue()).to(deadLetterExchange())
				.with(env.getProperty("rabbitmq.queue.deadloan.routingkey"));
	}

	@Bean
	Queue frontOfficeLoanVerificationQueue() {
		return QueueBuilder.durable(env.getProperty("rabbitmq.queue.frontoffice.verify"))
				.withArgument("x-dead-letter-exchange", env.getProperty("rabbitmq.exchnage.deadloanprocessing"))
				.withArgument("x-dead-letter-routing-key", env.getProperty("rabbitmq.queue.deadloan.routingkey"))
				.build();
	}

	@Bean
	Binding frontOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(frontOfficeLoanVerificationQueue()).to(exchange())
				.with(env.getProperty("rabbitmq.queue.frontoffice.verify.routingkey"));
	}

	@Bean
	Queue carOfficeLoanVerificationQueue() {
		return QueueBuilder.durable(env.getProperty("rabbitmq.queue.cardept.verify"))
				.withArgument("x-dead-letter-exchange", env.getProperty("rabbitmq.exchnage.deadloanprocessing"))
				.withArgument("x-dead-letter-routing-key", env.getProperty("rabbitmq.queue.deadloan.routingkey"))
				.build();
	}

	@Bean
	Binding carOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(carOfficeLoanVerificationQueue()).to(exchange())
				.with(env.getProperty("rabbitmq.queue.cardept.verify.routingkey"));
	}

	@Bean
	Queue riskOfficeLoanVerificationQueue() {
		return QueueBuilder.durable(env.getProperty("rabbitmq.queue.riskdept.verify"))
				.withArgument("x-dead-letter-exchange", env.getProperty("rabbitmq.exchnage.deadloanprocessing"))
				.withArgument("x-dead-letter-routing-key", env.getProperty("rabbitmq.queue.deadloan.routingkey"))
				.build();
	}

	@Bean
	Binding riskOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(riskOfficeLoanVerificationQueue()).to(exchange())
				.with(env.getProperty("rabbitmq.queue.riskdept.verify.routingkey"));
	}

	@Bean
	Queue disbursalOfficeLoanVerificationQueue() {
		return QueueBuilder.durable(env.getProperty("rabbitmq.queue.disbursaldept.verify"))
				.withArgument("x-dead-letter-exchange", env.getProperty("rabbitmq.exchnage.deadloanprocessing"))
				.withArgument("x-dead-letter-routing-key", env.getProperty("rabbitmq.queue.deadloan.routingkey"))
				.build();
	}

	@Bean
	Binding disbursalOfficeLoanVerificationQueueBinding() {
		return BindingBuilder.bind(disbursalOfficeLoanVerificationQueue()).to(exchange())
				.with(env.getProperty("rabbitmq.queue.disbursaldept.verify.routingkey"));
	}

	@Bean
	Queue loanConfirmationQueue() {
		return QueueBuilder.durable(env.getProperty("rabbitmq.queue.loanconfirmation"))
				.withArgument("x-dead-letter-exchange", env.getProperty("rabbitmq.exchnage.deadloanprocessing"))
				.withArgument("x-dead-letter-routing-key", env.getProperty("rabbitmq.queue.deadloan.routingkey"))
				.build();
	}

	@Bean
	Binding loanConfrimationQueueBinding() {
		return BindingBuilder.bind(loanConfirmationQueue()).to(exchange())
				.with(env.getProperty("rabbitmq.queue.loanconfirmation.routingkey"));
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
