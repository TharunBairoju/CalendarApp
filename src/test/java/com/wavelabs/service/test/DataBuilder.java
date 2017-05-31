package com.wavelabs.service.test;

import java.sql.Time;
import java.util.Date;

import com.wavelabs.model.Bookings;
import com.wavelabs.model.Provider;
import com.wavelabs.model.Receiver;
import com.wavelabs.model.SlotInfo;
import com.wavelabs.model.Status;
import com.wavelabs.model.TimeSlots;

public class DataBuilder {
	public static Bookings getBookings() {
		Bookings bookings = new Bookings();
		bookings.setId(1);
		bookings.setStatus(Status.available);
		bookings.setReceiver(getReceiver());
		bookings.setTimeslot(getTimeslot());
		return bookings;
	}

	@SuppressWarnings("deprecation")
	public static TimeSlots getTimeslot() {
		TimeSlots timeslot = new TimeSlots();
		timeslot.setId(1);
		timeslot.setFromTime(new Time(12, 20, 30));
		timeslot.setStatus(Status.available);
		timeslot.setReceiver(getReceiver());
		timeslot.setSlot(getSlotInfo());
		return timeslot;
	}

	public static Receiver getReceiver() {
		Receiver receiver = new Receiver();
		receiver.setId(100);
		receiver.setAddress("wavelabs");
		receiver.setEmail("tharun.it05@gmail.com");
		receiver.setName("tharun");
		receiver.setPassword("tharun");
		receiver.setPhoneNumber(9666253931l);
		return receiver;
	}

	public static Provider getProvider() {
		Provider provider = new Provider();
		provider.setId(100);
		provider.setAddress("wavelabs");
		provider.setEmail("tharun.it05@gmail.com");
		provider.setName("kiran");
		provider.setPassword("tharun");
		provider.setPhoneNumber(9666253931l);
		return provider;
	}

	@SuppressWarnings("deprecation")
	public static SlotInfo getSlotInfo() {
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setId(100);
		slotinfo.setDate(new Date(2017, 07, 30));
		slotinfo.setFromTime(new Time(12, 30, 30));
		slotinfo.setProvider(getProvider());
		slotinfo.setToTime(new Time(14, 30, 30));
		return slotinfo;
	}
}
