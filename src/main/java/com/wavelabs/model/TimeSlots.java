package com.wavelabs.model;

import java.sql.Time;


/**
 * This is a class Timeslots
 * 
 * @author tharunkumarb
 *
 */
public class TimeSlots {
	private int id;
	private Time fromTime;
	private Status status;
	private SlotInfo slot;
	private Receiver receiver;

	public TimeSlots() {

	}

	public SlotInfo getSlot() {
		return slot;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public void setSlot(SlotInfo slot) {
		this.slot = slot;
	}

	public Receiver getReceiver() {
		return receiver;
	}

	public void setReceiver(Receiver receiver) {
		this.receiver = receiver;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Time getFromTime() {
		return fromTime;
	}

	public void setFromTime(Time fromTime) {
		this.fromTime = fromTime;
	}
}
