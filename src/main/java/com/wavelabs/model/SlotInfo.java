package com.wavelabs.model;

import java.sql.Time;
import java.util.Date;


/**
 * This is model for Slotinfo
 * 
 * @author tharunkumarb
 *
 */
public class SlotInfo {
	private int id;
	private Date date;
	private Time fromTime;
	private Time toTime;
	private Provider provider;

	public SlotInfo() {

	}

	public Provider getProvider() {
		return provider;
	}

	public void setProvider(Provider provider) {
		this.provider = provider;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getFromTime() {
		return fromTime;
	}

	public void setFromTime(Time fromTime) {
		this.fromTime = fromTime;
	}

	public Time getToTime() {
		return toTime;
	}

	public void setToTime(Time toTime) {
		this.toTime = toTime;
	}

}
