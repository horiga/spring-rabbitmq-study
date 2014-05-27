package org.horiga.study.rabbitmq;

import org.horiga.study.rabbitmq.publisher.MessagePublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableConfigurationProperties
@EnableAutoConfiguration
public class Application implements CommandLineRunner {

	private static Logger log = LoggerFactory.getLogger(Application.class);
	
	@Autowired
	MessagePublisher publisher;
	
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

	@Override
	public void run(String... args) throws Exception {
		log.debug("RabbitMQ test/start");
		publisher.publishMessage("Hello world.");
		log.debug("RabbitMQ test/end");
	}
}