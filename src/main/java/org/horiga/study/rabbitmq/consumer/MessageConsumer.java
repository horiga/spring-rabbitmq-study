package org.horiga.study.rabbitmq.consumer;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class MessageConsumer {
	
	private static Logger log = LoggerFactory.getLogger(MessageConsumer.class);
	
	@Autowired
	AnnotationConfigApplicationContext context;
	
	public void handleMessage(String message) {
        log.error("### handleMessage <" + message + ">");
        /*
         * do anything...
         */
	}
	
	@PreDestroy
	public void releaseResources() {
		log.info("AMQP consumer : releaseResources");
		this.context.close();
	}
}
