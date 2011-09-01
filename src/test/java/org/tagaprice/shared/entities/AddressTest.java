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
		Assert.assertEquals(addressEmpty.getPos().getLat(), 0.0, 0.0);
		Assert.assertEquals(addressEmpty.getPos().getLon(), 0.0, 0.0);
	}


	@Test
	public void testGetLat() {
		Assert.assertEquals(withConstructor.getPos().getLat(), 1.1, 0.0);
	}

	@Test
	public void testGetLng() {
		Assert.assertEquals(withConstructor.getPos().getLon(), 2.2, 0.0);
	}

	@Test
	public void testSetLat() {
		toChange.getPos().setLat(3.3);
		Assert.assertEquals(toChange.getPos().getLat(), 3.3, 0.0);
	}

	@Test
	public void testSetLng() {
		toChange.getPos().setLon(4.4);
		Assert.assertEquals(toChange.getPos().getLon(), 4.4, 0.0);
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
