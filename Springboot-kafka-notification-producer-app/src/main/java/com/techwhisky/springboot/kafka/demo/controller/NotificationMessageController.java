package com.techwhisky.springboot.kafka.demo.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.techwhisky.springboot.kafka.demo.controller.request.NotificationRequest;
import com.techwhisky.springboot.kafka.demo.model.MessageType;
import com.techwhisky.springboot.kafka.demo.service.NotificationFactory;
import com.techwhisky.springboot.kafka.demo.service.NotificationStrategy;

@RestController
@RequestMapping("/notifications")
public class NotificationMessageController {

	@Autowired
	private NotificationFactory notificationFactory;

	@PostMapping("/publish/message")
	public ResponseEntity<String> sendNotificationToCustomer(@RequestBody NotificationRequest notificationRequest) {

		MessageType messageType = MessageType.valueOf(notificationRequest.getMessageType());
		Map<String, Object> messageContent = notificationRequest.getMessageContent();
		NotificationStrategy notificationStrategy = notificationFactory.getNotificationStrategyOnType(messageType);
		try {
			notificationStrategy.sendMessage(messageContent);
			return ResponseEntity.ok("notifications has been submitted to kafka topic " + messageType);
		} catch (Exception exception) {
			return ResponseEntity.internalServerError()
					.body("some error occurred while publishing data to kafka topic " + messageType);
		}

	}

}
