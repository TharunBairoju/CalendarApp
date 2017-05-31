package com.wavelabs.resources.test;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.resources.ProviderResource;
import com.wavelabs.service.ProviderService;
import com.wavelabs.service.test.DataBuilder;

@SuppressWarnings("rawtypes")
@RunWith(MockitoJUnitRunner.class)
public class ProviderResourceTest {
	@Mock
	ProviderService providerService;

	@InjectMocks
	private ProviderResource providerResource;

	@Test
	public void testPersistProvider1() {
		Provider provider = DataBuilder.getProvider();
		when(providerService.persistProvider(provider)).thenReturn(provider);
		ResponseEntity entity = providerResource.persistProvider(provider);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testPersistProvider2() {
		Provider provider = DataBuilder.getProvider();
		when(providerService.persistProvider(provider)).thenReturn(null);
		ResponseEntity entity = providerResource.persistProvider(provider);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testCreateSlots1() {
		List<SlotInfo> slots = new ArrayList<SlotInfo>();
		when(
				providerService.providerSlots(anyInt(), any(), any(), any(),
						any(), anyInt())).thenReturn(slots);
		ResponseEntity entity = providerResource.createSlots(1, "1/12/2017",
				"10/12/2017", "09:00:00", "12:00:00", 30);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testCreateSlots2() {
		when(
				providerService.providerSlots(anyInt(), any(), any(), any(),
						any(), anyInt())).thenReturn(null);
		ResponseEntity entity = providerResource.createSlots(1, "1/12/2017",
				"10/12/2017", "09:00:00", "12:00:00", 30);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateSlots3() {
		when(
				providerService.providerSlots(anyInt(), any(), any(), any(),
						any(), anyInt())).thenThrow(Exception.class);
		ResponseEntity entity = providerResource.createSlots(1, "1/12/2017",
				"10/12/2017", "09:00:00", "12:00:00", 30);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testCreateavailability1() {
		String ids = "1,2,3";
		String status = "done";
		when(providerService.createAvailability(ids)).thenReturn(status);
		ResponseEntity entity = providerResource.createavailability(ids);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testCreateavailability2() {
		String ids = "1,2,3";
		when(providerService.createAvailability(ids)).thenReturn(null);
		ResponseEntity entity = providerResource.createavailability(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateavailability3() {
		String ids = "1,2,3";
		when(providerService.createAvailability(ids))
				.thenThrow(Exception.class);
		ResponseEntity entity = providerResource.createavailability(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testGetProvidersList1() {
		List<Provider> provider = new ArrayList<Provider>();
		when(providerService.getAllProviders()).thenReturn(provider);
		ResponseEntity entity = providerResource.getProvidersList();
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testGetProvidersList2() {
		when(providerService.getAllProviders()).thenReturn(null);
		ResponseEntity entity = providerResource.getProvidersList();
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetProvidersList3() {
		when(providerService.getAllProviders()).thenThrow(Exception.class);
		ResponseEntity entity = providerResource.getProvidersList();
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testGetBookedSlotData1() {
		String data = "data";
		when(providerService.getBookedInformation(anyInt())).thenReturn(data);
		ResponseEntity entity = providerResource.getBookedSlotData(2);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testGetBookedSlotData2() {
		when(providerService.getBookedInformation(anyInt())).thenReturn(null);
		ResponseEntity entity = providerResource.getBookedSlotData(2);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetBookedSlotData3() {
		when(providerService.getBookedInformation(anyInt())).thenThrow(
				Exception.class);
		ResponseEntity entity = providerResource.getBookedSlotData(2);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

}
