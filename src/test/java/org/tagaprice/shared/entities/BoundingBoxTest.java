package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.Address.LatLon;

public class BoundingBoxTest {

	BoundingBox emptyBox;
	BoundingBox withConstructor;
	BoundingBox swappedFields;

	@Before
	public void setUp() throws Exception {
		emptyBox = new BoundingBox();
		withConstructor = new BoundingBox(1.1, 2.2, 3.3, 4.4);
		swappedFields = new BoundingBox(3.3, 4.4, 1.1, 2.2);
	}

	@Test
	public void testEmptyBoundingBox() {
		assertEquals(emptyBox.getSouthLat(), 0.0, 0.0);
		assertEquals(emptyBox.getNorthLat(), 0.0, 0.0);
		assertEquals(emptyBox.getWestLon(), 0.0, 0.0);
		assertEquals(emptyBox.getEastLon(), 0.0, 0.0);
	}

	@Test
	public void testGetters() {
		assertEquals(withConstructor.getSouthLat(), 1.1, 0.0);
		assertEquals(withConstructor.getWestLon(), 2.2, 0.0);
		assertEquals(withConstructor.getNorthLat(), 3.3, 0.0);
		assertEquals(withConstructor.getEastLon(), 4.4, 0.0);
	}

	@Test
	public void testContains() {
		// check points at the four corners
		assertTrue(withConstructor.contains(new LatLon(3.3, 4.4))); // north-east
		assertTrue(withConstructor.contains(new LatLon(3.3, 2.2))); // north-west
		assertTrue(withConstructor.contains(new LatLon(1.1, 4.4))); // south-east
		assertTrue(withConstructor.contains(new LatLon(1.1, 2.2))); // south-west

		// check point at the center
		assertTrue(withConstructor.contains(new LatLon(2.2, 3.3)));

		// check some points outside one boundary
		assertFalse(withConstructor.contains(new LatLon(0,3.3)));
		assertFalse(withConstructor.contains(new LatLon(5,3.3)));

		assertFalse(withConstructor.contains(new LatLon(2.2,0)));
		assertFalse(withConstructor.contains(new LatLon(2.2,5)));
	}

	@Test
	public void testSwapping() {
		assertEquals(swappedFields.getSouthLat(), 1.1, 0.0);
		assertEquals(swappedFields.getWestLon(), 2.2, 0.0);
		assertEquals(swappedFields.getNorthLat(), 3.3, 0.0);
		assertEquals(swappedFields.getEastLon(), 4.4, 0.0);
	}

	@Test
	public void testEquals() {
		assertEquals(withConstructor, swappedFields);
	}
}
