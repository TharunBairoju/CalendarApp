package com.wavelabs.service.test;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.wavelabs.dao.ReceiverDao;
import com.wavelabs.model.Receiver;
import com.wavelabs.service.ReceiverService;

@RunWith(MockitoJUnitRunner.class)
public class ReceiverServiceTest {

	@Mock
	ReceiverDao receiverDao;
	
	@InjectMocks
	private ReceiverService receiverService;
	
	@Test
	public void testCreateReceiver(){
		Receiver receiver=DataBuilder.getReceiver();
		when(receiverDao.persistReceiver(receiver)).thenReturn(receiver);
		Receiver receiver1=receiverService.createReceiver(receiver);
		Assert.assertEquals(receiver, receiver1);
	}
	
	@Test
	public void testGetReceiverById(){
		Receiver receiver=DataBuilder.getReceiver();
		when(receiverDao.getReceiverById(anyInt())).thenReturn(receiver);
		Receiver receiver1=receiverService.getReceiverById(1);
		Assert.assertEquals(receiver, receiver1);
	}
}
