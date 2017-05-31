package com.wavelabs.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Receiver;

public class ReceiverTest {
	@Test
	public void testGetId() {
		Receiver receiver = new Receiver();
		receiver.setId(100);
		Assert.assertEquals(100, receiver.getId());
	}

	@Test
	public void testGetName() {
		Receiver receiver = new Receiver();
		receiver.setName("Tharun");
		Assert.assertEquals("Tharun", receiver.getName());
	}

	@Test
	public void testGetEmail() {
		Receiver receiver = new Receiver();
		receiver.setEmail("tharun.it05@gmail.com");
		Assert.assertEquals("tharun.it05@gmail.com", receiver.getEmail());
	}

	@Test
	public void testGetPhone_number() {
		Receiver receiver = new Receiver();
		receiver.setPhoneNumber(9666253931l);
		Assert.assertEquals(9666253931l, receiver.getPhoneNumber());
	}

	@Test
	public void testGetPassword() {
		Receiver receiver = new Receiver();
		receiver.setPassword("password");
		Assert.assertEquals("password", receiver.getPassword());
	}

	@Test
	public void testGetAddress() {
		Receiver receiver = new Receiver();
		receiver.setAddress("plotNo:7 Jayaberi enclave,Hitech city");
		Assert.assertEquals("plotNo:7 Jayaberi enclave,Hitech city", receiver.getAddress());
	}

}
