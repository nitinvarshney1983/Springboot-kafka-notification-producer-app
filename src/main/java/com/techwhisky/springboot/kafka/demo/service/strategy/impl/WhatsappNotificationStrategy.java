package com.techwhisky.springboot.kafka.demo.service.strategy.impl;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import com.techwhisky.springboot.kafka.demo.model.Whatsapp;
import com.techwhisky.springboot.kafka.demo.service.NotificationStrategy;

@Component
public class WhatsappNotificationStrategy implements NotificationStrategy{

	@Value("${kafka.whatsapptopic.name}")
	private String topicName;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	@Override
	public void sendMessage(Map<String,Object> notificationData) {
		Whatsapp whatsapp=generateWhatsappContent(notificationData);
		CompletableFuture<SendResult<String,Object>> completableFuture=kafkaTemplate.send(topicName,whatsapp);
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

	private Whatsapp generateWhatsappContent(Map<String, Object> notificationData) {
		Whatsapp whatsapp=new Whatsapp();
		whatsapp.setSenderWhatsappNumber((String)notificationData.get("senderWhatsappNumber"));
		whatsapp.setReceiverWhatsappNumber((String)notificationData.get("receiverWhatsappNumber"));
		whatsapp.setContent((String)notificationData.get("content"));
		return whatsapp;
	}

}
