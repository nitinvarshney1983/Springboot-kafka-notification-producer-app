package com.techwhisky.springboot.kafka.demo.model;


public class SMS {

	private String senderNumber;
	private String receiverNumber;
	private String content;
	public String getSenderNumber() {
		return senderNumber;
	}
	public void setSendNumber(String senderNumber) {
		this.senderNumber = senderNumber;
	}
	public String getReceiverNumber() {
		return receiverNumber;
	}
	public void setReceiverNumber(String receiverNumber) {
		this.receiverNumber = receiverNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
