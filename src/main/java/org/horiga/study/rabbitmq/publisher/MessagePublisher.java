package org.horiga.study.rabbitmq.publisher;

import org.horiga.study.rabbitmq.consumer.MessageConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class MessagePublisher {

	private static Logger log = LoggerFactory.getLogger(MessagePublisher.class);

	@Value("amqp.publisher.default.queueName")
	String queueName;
	//static String queueName = "sb-queue";
	
	@Value("amqp.publisher.default.exchangeName")
	String exchangeName;
	//static String exchangeName = "sb-exchange";
	
	static boolean autoDelete = true; // default value is 'false'

	static boolean durable = true; // default is 'true'

	@Autowired
	RabbitTemplate rabbitTemplate;

	@Bean
	Queue queue() {
		return new Queue(queueName, durable);
	}

	@Bean
	TopicExchange exchange() {
		// new DirectExchange(exchangeName, durable, autoDelete);
		// new FederatedExchange(exchangeName, durable, autoDelete);
		return new TopicExchange(exchangeName, durable, autoDelete);
	}

	@Bean
	Binding binding(Queue queue, TopicExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MessageConsumer consumer) {
		// default listener method name is 'handleMessage'
		return new MessageListenerAdapter(consumer);
	}

	@Bean
	SimpleMessageListenerContainer container(
			ConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {

		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();

		// Default connection is localhost and default amqp port.
		// create instance with
		// 'org.springframework.amqp.rabbit.connection.CachingConnectionFactory'.
		container.setConnectionFactory(connectionFactory);

		// container.setAcknowledgeMode(AcknowledgeMode.AUTO);
		
		container.setQueueNames(queueName);
		container.setMessageListener(listenerAdapter);

		return container;
	}

	public void publishMessage(String message) throws Exception {
		Thread.sleep(5000);
		String routingKey = queueName;
		log.info("send message...");
		rabbitTemplate.convertAndSend(routingKey, message);
	}
}
