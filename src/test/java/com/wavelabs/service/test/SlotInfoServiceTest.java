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

import com.wavelabs.dao.SlotInfoDao;
import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.service.SlotInfoService;

@RunWith(MockitoJUnitRunner.class)
public class SlotInfoServiceTest {

	@Mock
	SlotInfoDao slotInfoDao;

	@InjectMocks
	private SlotInfoService slotinfoService;

	@SuppressWarnings("deprecation")
	@Test
	public void testCreateSlots() {
		List<SlotInfo> slotsList = new ArrayList<SlotInfo>();
		Provider provider = DataBuilder.getProvider();
		Date fromdate = new Date(2017, 8, 3);
		Date todate = new Date(2017, 8, 10);
		Time fromtime = new Time(9, 00, 00);
		Time totime = new Time(12, 00, 00);
		when(
				slotInfoDao.persistSlots(any(), any(), any(), any(), any(),
						anyInt())).thenReturn(slotsList);
		List<SlotInfo> slots = slotinfoService.createSlots(provider, fromdate,
				todate, fromtime, totime, 30);
		Assert.assertEquals(slotsList.size(), slots.size());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetListOfDates() {
		Date fromdate = new Date(2017, 8, 3);
		Date todate = new Date(2017, 8, 10);
		List<Date> datesList = slotinfoService.getListOfDates(fromdate, todate);
		Assert.assertEquals(datesList, datesList);
	}

	@Test
	public void testGetAllSlotData() {
		List<SlotInfo> slotsList = new ArrayList<SlotInfo>();
		when(slotInfoDao.getAllSlotDetails()).thenReturn(slotsList);
		List<SlotInfo> slots = slotinfoService.getAllSlotData();
		Assert.assertEquals(slotsList, slots);
	}
}
