package com.wavelabs.model.test;

import org.junit.Assert;
import org.junit.Test;

import com.wavelabs.model.Provider;

public class ProviderTest {

	@Test
	public void testGetId() {
		Provider provider = new Provider();
		provider.setId(100);
		Assert.assertEquals(100, provider.getId());
	}

	@Test
	public void testGetName() {
		Provider provider = new Provider();
		provider.setName("Tharun");
		Assert.assertEquals("Tharun", provider.getName());
	}

	@Test
	public void testGetPhoneNumber() {
		Provider provider = new Provider();
		provider.setPhoneNumber(9666253931l);
		Assert.assertEquals(9666253931l, provider.getPhoneNumber());
	}

	@Test
	public void testGetEmail() {
		Provider provider = new Provider();
		provider.setEmail("tharun.it05@gmail.com");
		Assert.assertEquals("tharun.it05@gmail.com", provider.getEmail());
	}

	@Test
	public void testGetAddress() {
		Provider provider = new Provider();
		provider.setAddress("wavelabs");
		Assert.assertEquals("wavelabs", provider.getAddress());
	}

	@Test
	public void testGetPassword() {
		Provider provider = new Provider();
		provider.setPassword("tharun");
		Assert.assertEquals("tharun", provider.getPassword());
	}
}
