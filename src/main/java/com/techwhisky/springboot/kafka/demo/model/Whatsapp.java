package com.techwhisky.springboot.kafka.demo.model;


public class Whatsapp {

	private String senderWhatsappNumber;
	private String receiverWhatsappNumber;
	private String content;
	public String getSenderWhatsappNumber() {
		return senderWhatsappNumber;
	}
	public void setSenderWhatsappNumber(String senderWhatsappNumber) {
		this.senderWhatsappNumber = senderWhatsappNumber;
	}
	public String getReceiverWhatsappNumber() {
		return receiverWhatsappNumber;
	}
	public void setReceiverWhatsappNumber(String receiverWhatsappNumber) {
		this.receiverWhatsappNumber = receiverWhatsappNumber;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	
}
