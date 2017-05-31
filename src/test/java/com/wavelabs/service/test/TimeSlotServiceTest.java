package com.wavelabs.service.test;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.wavelabs.dao.TimeSlotDao;
import com.wavelabs.model.Ids;
import com.wavelabs.model.Provider;
import com.wavelabs.model.TimeSlots;
import com.wavelabs.service.TimeSlotService;

@RunWith(MockitoJUnitRunner.class)
public class TimeSlotServiceTest {

	@Mock
	TimeSlotDao timeslotDao;

	@InjectMocks
	private TimeSlotService timeslotServie;

	@SuppressWarnings("deprecation")
	@Test
	public void testGetNoOfSlots() {
		Time starttime = new Time(9, 00, 00);
		Time endtime = new Time(12, 00, 00);
		int value = 6;
		int count = timeslotServie.getNoOfSlots(starttime, endtime, 30);
		Assert.assertEquals(count, value);
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetTimings() {
		Time starttime = new Time(9, 00, 00);
		Time endtime = new Time(12, 00, 00);
		List<Time> times = timeslotServie.getTimings(starttime, endtime, 30);
		Assert.assertEquals(times.size(), 7);
	}

	@Test
	public void testGetTimeSlotById() {
		TimeSlots timeslot = DataBuilder.getTimeslot();
		when(timeslotDao.getTimeslotById(anyInt())).thenReturn(timeslot);
		TimeSlots timeslot1 = timeslotServie.getTimeSlotById(1);
		Assert.assertEquals(timeslot, timeslot1);
	}

	@Test
	public void testGetAllAvailabletimes() {
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(timeslotDao.getAllTimeSlots()).thenReturn(timeslotList);
		List<TimeSlots> list = timeslotServie.getAllTimeSlots();
		Assert.assertEquals(list.size(), timeslotList.size());
	}

	@Test
	public void testGetAllBookedtimes() {
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(timeslotDao.getAllAvailableTimeSlots()).thenReturn(timeslotList);
		List<TimeSlots> list = timeslotServie.getAllAvailabletimes();
		Assert.assertEquals(list.size(), timeslotList.size());
	}

	@Test
	public void testGetAllTimeSlots() {
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(timeslotDao.getAllTimeSlots()).thenReturn(timeslotList);
		List<TimeSlots> list = timeslotServie.getAllTimeSlots();
		Assert.assertEquals(list.size(), timeslotList.size());
	}

	@Test
	public void testConfirmBooking() {
		Ids id = new Ids();
		id.setReceiverId(1);
		id.setTimeslotId(1);
		Ids[] ids = { id };
		when(timeslotDao.confirmBooking(ids)).thenReturn("success");
		String msg = timeslotServie.confirmBooking(ids);
		Assert.assertEquals(msg, "success");
	}

	@Test
	public void testCancelBooking() {
		Ids id = new Ids();
		id.setReceiverId(1);
		id.setTimeslotId(1);
		Ids[] ids = { id };
		when(timeslotDao.cancelBooking(ids)).thenReturn("success");
		String msg = timeslotServie.cancelBooking(ids);
		Assert.assertEquals(msg, "success");
	}

	@Test
	public void testGetIds() {
		String ids = "1,2,3";
		int[] id = timeslotServie.getIds(ids);
		Assert.assertEquals(id.length, 3);
	}

	@Test
	public void testCreateAvailability() {
		String ids = "1,2,3";
		int[] id = timeslotServie.getIds(ids);
		when(timeslotDao.createAvailability(id)).thenReturn("done");
		String msg = timeslotServie.createAvailability(ids);
		Assert.assertEquals(msg, "done");
	}

	@Test
	public void testGetTimeSlotList() {
		String ids = "1,2,3";
		int[] id = timeslotServie.getIds(ids);
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(timeslotDao.getTimeslots(id)).thenReturn(timeslotList);
		List<TimeSlots> list = timeslotServie.getTimeSlotList(id);
		Assert.assertEquals(list.size(), timeslotList.size());
	}
	
	@Test
	public void testGetTimeslotsByProvider(){
		Provider provider=DataBuilder.getProvider();
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(timeslotDao.getTimeslotsList(provider)).thenReturn(timeslotList);
		List<TimeSlots> list = timeslotServie.getTimeSlotList(provider);
		Assert.assertEquals(list.size(), timeslotList.size());
	}
	
	@Test
	public void testGetBookedSlotData(){
		Provider provider=DataBuilder.getProvider();
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(timeslotDao.getTimeslotsList(provider)).thenReturn(timeslotList);
		List<TimeSlots> list = timeslotServie.getBookedSlotData(provider);
		Assert.assertEquals(list.size(), timeslotList.size());
	}
	
	@Test
	public void testGetBookingInfo(){
		Provider provider=DataBuilder.getProvider();
		List<TimeSlots> timeslotList = new ArrayList<TimeSlots>();
		timeslotList.add(DataBuilder.getTimeslot());
		when(timeslotDao.getTimeslotsBookingInfo(provider)).thenReturn(timeslotList);
		List<TimeSlots> list = timeslotServie.getBookingInfo(provider);
		Assert.assertEquals(list.size(), timeslotList.size());
	}
	@Test
	public void testModifyBooking(){
		String ids="1,2,3";
		String msg="done";
		when(timeslotDao.modifyBookings(ids)).thenReturn(msg);
		String value=timeslotServie.modifyBooking(ids);
		Assert.assertEquals(value, msg);
	}
	
	@Test
	public void testDeclineBooking(){
		String ids="1,2,3";
		String msg="done";
		when(timeslotDao.declineBookings(ids)).thenReturn(msg);
		String value=timeslotServie.declineBooking(ids);
		Assert.assertEquals(value, msg);
	}
}