package com.wavelabs.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Bookings;
import com.wavelabs.model.Receiver;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;

public class BookingsTest {

	@Test
	public void testGetId() {
		Bookings bookings = new Bookings();
		bookings.setId(10);
		Assert.assertEquals(10, bookings.getId());
	}

	@Test
	public void testGetTimeslot() {
		Bookings bookings = new Bookings();
		TimeSlots timeslot = new TimeSlots();
		timeslot.setId(10);
		bookings.setTimeslot(timeslot);
		Assert.assertEquals(10, bookings.getTimeslot().getId());
	}

	@Test
	public void testGetStatus() {
		Bookings bookings = new Bookings();
		bookings.setStatus(Status.booked);
		Assert.assertEquals(Status.booked, bookings.getStatus());
	}

	@Test
	public void testGetReceiver() {
		Bookings bookings = new Bookings();
		Receiver receiver = new Receiver();
		receiver.setId(10);
		bookings.setReceiver(receiver);
		Assert.assertEquals(10, bookings.getReceiver().getId());
	}
}
