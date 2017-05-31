package com.wavelabs.service.test;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.any;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.wavelabs.dao.ProviderDao;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.service.ProviderService;
import com.wavelabs.service.SlotInfoService;
import com.wavelabs.service.TimeSlotService;

@RunWith(MockitoJUnitRunner.class)
public class ProviderServiceTest {

	@Mock
	ProviderDao providerDao;
	
	@Mock
	TimeSlotService timeslotService;

	@Mock
	SlotInfoService slotinfoService;

	@InjectMocks
	private ProviderService providerService;

	@Test
	public void testPersistProvider() {
		Provider provider = DataBuilder.getProvider();
		when(providerDao.persistProvider(provider)).thenReturn(provider);
		Provider providers = providerService.persistProvider(provider);
		Assert.assertEquals(provider, providers);
	}

	@Test
	public void testGetProviderById() {
		Provider provider = DataBuilder.getProvider();
		when(providerDao.getProvider(anyInt())).thenReturn(provider);
		Provider providers = providerService.getProviderById(1);
		Assert.assertEquals(provider, providers);
	}

	@Test
	public void testProviderSlots() {
		List<SlotInfo> slotList = new ArrayList<SlotInfo>();
		slotList.add(DataBuilder.getSlotInfo());
		Time time = new Time(25000);
		Date date = new Date(25000);
		Provider provider = DataBuilder.getProvider();
		when(providerService.getProviderById(anyInt())).thenReturn(provider);
		when(
				slotinfoService.createSlots(any(), any(), any(), any(), any(),
						anyInt())).thenReturn(slotList);
		List<SlotInfo> slots = providerService.providerSlots(1, date, date,
				time, time, 1);
		Assert.assertEquals(slots, slotList);
	}

	@Test
	public void testGetAllProviders() {
		List<Provider> providerList = new ArrayList<Provider>();
		providerList.add(DataBuilder.getProvider());
		when(providerDao.getProvidersList()).thenReturn(providerList);
		List<Provider> providers = providerService.getAllProviders();
		Assert.assertEquals(providerList, providers);
	}
	
	@Test
	public void testGetTimeSlot(){
		List<TimeSlots> timeslots=new ArrayList<TimeSlots>();
		timeslots.add(DataBuilder.getTimeslot());
		Provider provider=DataBuilder.getProvider();
		when(providerService.getProviderById(anyInt())).thenReturn(provider);
		when(timeslotService.getTimeSlotList(provider)).thenReturn(timeslots);
		List<TimeSlots>timeslotsList=providerService.getTimeslotList(1);
		Assert.assertEquals(timeslots, timeslotsList);
	}
}
