package com.techwhisky.springboot.kafka.demo.controller.request;

import java.util.Map;

import com.techwhisky.springboot.kafka.demo.model.MessageType;




public class NotificationRequest {
	
	private String messageType;
	private Map<String,Object> messageContent;
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Map<String, Object> getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(Map<String, Object> messageContent) {
		this.messageContent = messageContent;
	}
	
	

}
