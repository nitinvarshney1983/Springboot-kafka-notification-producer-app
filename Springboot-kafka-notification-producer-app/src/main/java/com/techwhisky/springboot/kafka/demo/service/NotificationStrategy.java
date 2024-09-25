package com.techwhisky.springboot.kafka.demo.service;

import java.util.Map;

public interface NotificationStrategy {

	void sendMessage(Map<String,Object> notificationData);
	
}
