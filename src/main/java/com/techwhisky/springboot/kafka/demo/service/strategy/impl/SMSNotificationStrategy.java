package com.techwhisky.springboot.kafka.demo.service.strategy.impl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.techwhisky.springboot.kafka.demo.model.SMS;
import com.techwhisky.springboot.kafka.demo.service.NotificationStrategy;

@Component
public class SMSNotificationStrategy implements NotificationStrategy{

	@Value("${kafka.smstopic.name}")
	private String topicName;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void sendMessage(Map<String,Object> notificationData) {
		SMS sms=generateSMSContent(notificationData);
		 CompletableFuture<SendResult<String,Object>> completableFuture=kafkaTemplate.send(topicName,sms);
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

	private SMS generateSMSContent(Map<String, Object> notificationData) {
		SMS sms=new SMS();
		sms.setSendNumber((String)notificationData.get("senderNumber"));
		sms.setReceiverNumber((String)notificationData.get("receiverNumber"));
		sms.setContent((String)notificationData.get("content"));
		return sms;
	}


}
