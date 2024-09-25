package com.techwhisky.springboot.kafka.demo.service.strategy.impl;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.techwhisky.springboot.kafka.demo.model.Email;
import com.techwhisky.springboot.kafka.demo.service.NotificationStrategy;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {

	@Value("${kafka.emailtopic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void sendMessage(Map<String,Object> notificationData) {
		Email email=generateEmailContent(notificationData);
		 CompletableFuture<SendResult<String,Object>> completableFuture=kafkaTemplate.send(topicName,email);
		 completableFuture.whenCompleteAsync((result,ex)->{
			 if(ex==null) {
				 System.out.println("Sent Message=["+notificationData+"] to partition "
						 +result.getRecordMetadata().partition()+" on offset "
						 +result.getRecordMetadata()+" of topic "+topicName);
			 }else {
				 System.err.println("Unable to send message to topic "+topicName +" due to :"+ex.getMessage());
			 }
		 });
		
	}

	private Email generateEmailContent(Map<String, Object> notificationData) {
		Email email = new Email();
		email.setFrom((String) notificationData.get("from"));
		String[] emailTo = ((String) notificationData.get("to")).split("\\,");
		email.setTo(Arrays.asList(emailTo));
		String[] emailCC = ((String) notificationData.get("cc")).split("\\,");
		email.setCc(Arrays.asList(emailCC));
		String[] emailBcc = ((String) notificationData.get("bcc")).split("\\,");
		email.setBcc(Arrays.asList(emailBcc));
		email.setContent((String) notificationData.get("content"));
		return email;
	}

}
