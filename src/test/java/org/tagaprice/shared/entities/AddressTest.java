package org.tagaprice.shared.entities;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class AddressTest {

	Address addressEmpty;
	Address withConstructor;
	Address toChange;

	@Before
	public void setUp() throws Exception {
		addressEmpty = new Address();
		withConstructor = new Address("address", 1.1, 2.2);
		toChange = new Address();
	}

	@Test
	public void testAddress() {
		assertEquals(addressEmpty.getAddress(), null);
		Assert.assertEquals(addressEmpty.getLat(), 0.0, 0.0);
		Assert.assertEquals(addressEmpty.getLon(), 0.0, 0.0);
	}


	@Test
	public void testGetLat() {
		Assert.assertEquals(withConstructor.getLat(), 1.1, 0.0);
	}

	@Test
	public void testGetLng() {
		Assert.assertEquals(withConstructor.getLon(), 2.2, 0.0);
	}

	@Test
	public void testSetLat() {
		toChange.setLat(3.3);
		Assert.assertEquals(toChange.getLat(), 3.3, 0.0);
	}

	@Test
	public void testSetLng() {
		toChange.setLon(4.4);
		Assert.assertEquals(toChange.getLon(), 4.4, 0.0);
	}

	@Test
	public void testGetAddress() {
		assertEquals(withConstructor.getAddress(), "address");
	}

	@Test
	public void testSetAddress() {
		toChange.setAddress("address");
		assertEquals(toChange.getAddress(), "address");
	}

}
