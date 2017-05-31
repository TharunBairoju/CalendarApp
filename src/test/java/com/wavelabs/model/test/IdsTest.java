package com.wavelabs.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Ids;

public class IdsTest {
	@Test
	public void testGetReceiverId() {
		Ids ids = new Ids();
		ids.setReceiverId(111);
		Assert.assertEquals(111, ids.getReceiverId());
	}

	@Test
	public void testGetTimeslotId() {
		Ids ids = new Ids();
		ids.setTimeslotId(111);
		Assert.assertEquals(111, ids.getTimeslotId());
	}
}
