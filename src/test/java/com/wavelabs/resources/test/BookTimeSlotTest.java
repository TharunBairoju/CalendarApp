package com.wavelabs.resources.test;

import static org.mockito.Mockito.when;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import com.wavelabs.dao.BookingsDao;
import com.wavelabs.model.Bookings;
import com.wavelabs.model.Ids;
import com.wavelabs.resources.BookTimeSlot;
import com.wavelabs.service.BookingService;
import com.wavelabs.service.TimeSlotService;

@SuppressWarnings("rawtypes")
@RunWith(MockitoJUnitRunner.class)
public class BookTimeSlotTest {
	@Mock
	BookingsDao bookingsDao;

	@Mock
	BookingService bookingService;

	@Mock
	TimeSlotService timeslotService;

	@InjectMocks
	private BookTimeSlot bookTimeslot;

	@Test
	public void testBookTimeSlot1() {
		String status = "done";
		when(timeslotService.bookTimeSlot(anyInt(), any())).thenReturn(status);
		ResponseEntity entity = bookTimeslot.bookTimeSlot(1, "1,2");
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testBookTimeSlot2() {
		when(timeslotService.bookTimeSlot(anyInt(), any())).thenReturn(null);
		ResponseEntity entity = bookTimeslot.bookTimeSlot(1, "1,2");
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testBookTimeSlot3() {
		when(timeslotService.bookTimeSlot(anyInt(), any())).thenThrow(
				Exception.class);
		ResponseEntity entity = bookTimeslot.bookTimeSlot(1, "1,2");
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testConfirmTimeSlot1() {
		Ids id = new Ids();
		Ids[] ids = { id };
		String status = "done";
		when(timeslotService.confirmBooking(any())).thenReturn(status);
		ResponseEntity entity = bookTimeslot.confirmTimeSlot(ids);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testcConfirmTimeSlot2() {
		Ids id = new Ids();
		Ids[] ids = { id };
		when(timeslotService.confirmBooking(any())).thenReturn(null);
		ResponseEntity entity = bookTimeslot.confirmTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testConfirmTimeSlot3() {
		Ids id = new Ids();
		Ids[] ids = { id };
		when(timeslotService.confirmBooking(any())).thenThrow(Exception.class);
		ResponseEntity entity = bookTimeslot.confirmTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testCancelTimeSlot1() {
		Ids id = new Ids();
		Ids[] ids = { id };
		String status = "done";
		when(timeslotService.cancelBooking(any())).thenReturn(status);
		ResponseEntity entity = bookTimeslot.cancelTimeSlot(ids);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testcCancelTimeSlot2() {
		Ids id = new Ids();
		Ids[] ids = { id };
		when(timeslotService.cancelBooking(any())).thenReturn(null);
		ResponseEntity entity = bookTimeslot.cancelTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCancelTimeSlot3() {
		Ids id = new Ids();
		Ids[] ids = { id };
		when(timeslotService.cancelBooking(any())).thenThrow(Exception.class);
		ResponseEntity entity = bookTimeslot.cancelTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testModifyTimeSlot1() {
		String ids = "1,2,3";
		String status = "done";
		when(timeslotService.modifyBooking(any())).thenReturn(status);
		ResponseEntity entity = bookTimeslot.modifyTimeSlot(ids);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testcModifyTimeSlot2() {
		String ids = "1,2,3";
		when(timeslotService.modifyBooking(any())).thenReturn(null);
		ResponseEntity entity = bookTimeslot.modifyTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testModifyTimeSlot3() {
		String ids = "1,2,3";
		when(timeslotService.modifyBooking(any())).thenThrow(Exception.class);
		ResponseEntity entity = bookTimeslot.modifyTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testDeclineTimeSlot1() {
		String ids = "1,2,3";
		String status = "done";
		when(timeslotService.declineBooking(any())).thenReturn(status);
		ResponseEntity entity = bookTimeslot.declineTimeSlot(ids);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testcDeclineTimeSlot2() {
		String ids = "1,2,3";
		when(timeslotService.declineBooking(any())).thenReturn(null);
		ResponseEntity entity = bookTimeslot.declineTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeclineTimeSlot3() {
		String ids = "1,2,3";
		when(timeslotService.declineBooking(any())).thenThrow(Exception.class);
		ResponseEntity entity = bookTimeslot.declineTimeSlot(ids);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@Test
	public void testGetBookings1() {
		List<Bookings> bookings = new ArrayList<Bookings>();
		when(bookingService.getBookingsById(anyInt())).thenReturn(bookings);
		ResponseEntity entity = bookTimeslot.getBookings(2);
		Assert.assertEquals(200, entity.getStatusCodeValue());
	}

	@Test
	public void testGetBookings2() {
		when(bookingService.getBookingsById(anyInt())).thenReturn(null);
		ResponseEntity entity = bookTimeslot.getBookings(2);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testGetBookings3() {
		when(bookingService.getBookingsById(anyInt())).thenThrow(
				Exception.class);
		ResponseEntity entity = bookTimeslot.getBookings(2);
		Assert.assertEquals(500, entity.getStatusCodeValue());
	}

}
