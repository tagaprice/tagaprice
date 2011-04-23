package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class QuantityTest {

	Quantity emptyQuantity;
	Quantity newQuantity;
	Quantity setterQuantity;

	@Before
	public void setUp() throws Exception {
		emptyQuantity = new Quantity();
		newQuantity = new Quantity(15.3, new Unit("kg"));
		setterQuantity = new Quantity();
	}

	@Test
	public void testQuantity() {
		assertNull(emptyQuantity.getUnit());
		assertEquals(emptyQuantity.getQuantity(), 0.0, 0.0);
	}


	@Test
	public void testSetQuantity() {
		setterQuantity.setQuantity(19.3);
		assertEquals(setterQuantity.getQuantity(), 19.3, 0.0);
	}

	@Test
	public void testGetQuantity() {
		assertEquals(newQuantity.getQuantity(), 15.3, 0.0);

	}

	@Test
	public void testSetUnit() {
		setterQuantity.setUnit(new Unit("g"));
		assertNotNull(setterQuantity.getUnit());
		assertEquals(setterQuantity.getUnit().getTitle(), "g");
	}

	@Test
	public void testGetUnit() {
		assertNotNull(newQuantity.getUnit());
		assertEquals(newQuantity.getUnit().getTitle(), "kg");

	}

}
