package com.wavelabs.resources.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.wavelabs.model.Receiver;
import com.wavelabs.resources.ProviderResource;
import com.wavelabs.service.ReceiverService;
import com.wavelabs.service.test.DataBuilder;

@RunWith(MockitoJUnitRunner.class)
public class ReceiverResourceTest {

	@Mock
	ReceiverService receiverService;

	@InjectMocks
	private ProviderResource providerResource;

	@Test
	public void testPersistReceiver() {
		Receiver receiver=DataBuilder.getReceiver();
		
	}
}