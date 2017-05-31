package com.wavelabs.service.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.wavelabs.service.CommunicationService;

@RunWith(MockitoJUnitRunner.class)
public class CommunicationServiceTest {

	@InjectMocks
	private CommunicationService communicationService;

	@Test
	public void testGetSuccessMessage() {
		String rname = "tharun";
		String pname = "kumar";
		String time = "11:30";
		String message = communicationService.getSuccessMessage(rname, pname,
				time);
		Assert.assertNotEquals(message, null);
	}

	@Test
	public void testGetFailureMessage() {
		String rname = "tharun";
		String pname = "kumar";
		String time = "11:30";
		String message = communicationService.getFailureMessage(rname, pname,
				time);
		Assert.assertNotEquals(message, null);
	}

	@Test
	public void testGetProcessMessage() {
		String rname = "tharun";
		String pname = "kumar";
		String time = "11:30";
		String message = communicationService.getProcessMessage(rname, pname,
				time);
		Assert.assertNotEquals(message, null);
	}

	@Test
	public void testGetDeclineMessage() {
		String rname = "tharun";
		String pname = "kumar";
		String time = "11:30";
		String message = communicationService.getDeclineMessage(rname, pname,
				time);
		Assert.assertNotEquals(message, null);
	}
}
