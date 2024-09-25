package com.techwhisky.springboot.kafka.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.techwhisky.springboot.kafka.demo.model.MessageType;
import com.techwhisky.springboot.kafka.demo.service.strategy.impl.EmailNotificationStrategy;
import com.techwhisky.springboot.kafka.demo.service.strategy.impl.SMSNotificationStrategy;
import com.techwhisky.springboot.kafka.demo.service.strategy.impl.WhatsappNotificationStrategy;

@Component
public class NotificationFactory {

	@Autowired
	private ApplicationContext context;
	
	public NotificationStrategy getNotificationStrategyOnType(MessageType messageType) {
	
		switch (messageType) {
		case EMAIL: {
			return context.getBean(EmailNotificationStrategy.class);
		}
		case SMS:{
			return context.getBean(SMSNotificationStrategy.class);
		}
		case WHATSAPP:{
			return context.getBean(WhatsappNotificationStrategy.class);
		}
		default:
			throw new IllegalArgumentException("Unexpected value: " + messageType);
		}

	}
}
