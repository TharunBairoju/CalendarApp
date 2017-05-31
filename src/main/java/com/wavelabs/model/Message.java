package com.wavelabs.model;


/**
 * This is a model class Message
 * 
 * @author tharunkumarb
 *
 */
public class Message {
	public Message() {

	}

	private int status;
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
