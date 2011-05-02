package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.accountmanagement.User;

public class QuantityTest {

	Quantity emptyQuantity;
	Quantity newQuantity;
	Quantity setterQuantity;
	
	User testUser = new User("testUID", "testRev", "Test User");

	@Before
	public void setUp() throws Exception {
		emptyQuantity = new Quantity();
		newQuantity = new Quantity(new BigDecimal("15.3"), new Unit(testUser, "kg"));
		setterQuantity = new Quantity();
	}

	@Test
	public void testQuantity() {
		assertNull(emptyQuantity.getUnit());
		assertEquals(emptyQuantity.getQuantity(), new BigDecimal("0.0"));
	}


	@Test
	public void testSetQuantity() {
		setterQuantity.setQuantity(new BigDecimal("19.3"));
		assertEquals(setterQuantity.getQuantity(), new BigDecimal("19.3"));
	}

	@Test
	public void testGetQuantity() {
		assertEquals(newQuantity.getQuantity(), new BigDecimal("15.3"));

	}

	@Test
	public void testSetUnit() {
		setterQuantity.setUnit(new Unit(testUser, "g"));
		assertNotNull(setterQuantity.getUnit());
		assertEquals(setterQuantity.getUnit().getTitle(), "g");
	}

	@Test
	public void testGetUnit() {
		assertNotNull(newQuantity.getUnit());
		assertEquals(newQuantity.getUnit().getTitle(), "kg");

	}

}
