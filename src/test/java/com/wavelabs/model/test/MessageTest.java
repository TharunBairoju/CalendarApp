package com.wavelabs.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Message;

public class MessageTest {
	@Test
	public void testGetStatus() {
		Message message = new Message();
		message.setStatus(500);
		Assert.assertEquals(500, message.getStatus());
	}

	@Test
	public void testGetMessage() {
		Message message = new Message();
		message.setMessage("message");
		Assert.assertEquals("message", message.getMessage());
	}
}
