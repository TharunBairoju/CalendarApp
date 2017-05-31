package com.wavelabs.model.test;

import java.sql.Time;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Receiver;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;

public class TimeSlotTest {

	@Test
	public void testGetId() {
		TimeSlots timeslot = new TimeSlots();
		timeslot.setId(111);
		Assert.assertEquals(111, timeslot.getId());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetFromTime() {
		TimeSlots timeslot = new TimeSlots();
		timeslot.setFromTime(new Time(12, 20, 30));
		Assert.assertEquals(new Time(12, 20, 30), timeslot.getFromTime());
	}

	@Test
	public void testGetStatus() {
		TimeSlots timeslot = new TimeSlots();
		timeslot.setStatus(Status.booked);
		Assert.assertEquals(Status.booked, timeslot.getStatus());
	}

	@Test
	public void testGetSlot() {
		TimeSlots timeslot = new TimeSlots();
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setId(111);
		timeslot.setSlot(slotinfo);
		Assert.assertEquals(slotinfo, timeslot.getSlot());
	}

	@Test
	public void testGetReceiver() {
		TimeSlots timeslot = new TimeSlots();
		Receiver receiver = new Receiver();
		receiver.setId(111);
		timeslot.setReceiver(receiver);
		Assert.assertEquals(receiver, timeslot.getReceiver());
	}

}
