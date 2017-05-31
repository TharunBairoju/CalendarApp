package com.wavelabs.model.test;

import java.sql.Time;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Provider;
import com.wavelabs.model.SlotInfo;

public class SlotInfoTest {
	@Test
	public void testGetId() {
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setId(100);
		Assert.assertEquals(100, slotinfo.getId());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetDate() {
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setDate(new Date(2017, 07, 29));
		Assert.assertEquals(new Date(2017, 07, 29), slotinfo.getDate());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetFromTime() {
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setFromTime(new Time(12, 20, 30));
		Assert.assertEquals(new Time(12, 20, 30), slotinfo.getFromTime());
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testToTime() {
		SlotInfo slotinfo = new SlotInfo();
		slotinfo.setToTime(new Time(12, 20, 30));
		Assert.assertEquals(new Time(12, 20, 30), slotinfo.getToTime());
	}

	@Test
	public void testGetProvider() {
		SlotInfo slotinfo = new SlotInfo();
		Provider provider = new Provider();
		provider.setName("tharun");
		slotinfo.setProvider(provider);
		Assert.assertEquals("tharun", slotinfo.getProvider().getName());
	}
}
