package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class BoundingBoxTest {

	BoundingBox emptyBox;
	BoundingBox withConstructor;

	@Before
	public void setUp() throws Exception {
		emptyBox = new BoundingBox();
		withConstructor = new BoundingBox(1.1, 2.2, 3.3, 4.4);
	}

	@Test
	public void testBoundingBox() {
		assertEquals(emptyBox.getSouthLat(), 0.0, 0.0);
		assertEquals(emptyBox.getNorthLat(), 0.0, 0.0);
		assertEquals(emptyBox.getWestLon(), 0.0, 0.0);
		assertEquals(emptyBox.getEastLon(), 0.0, 0.0);
	}


	@Test
	public void testGetX1() {
		assertEquals(withConstructor.getSouthLat(), 1.1, 0.0);
	}

	@Test
	public void testGetY1() {
		assertEquals(withConstructor.getWestLon(), 2.2, 0.0);
	}

	@Test
	public void testGetX2() {
		assertEquals(withConstructor.getNorthLat(), 3.3, 0.0);
	}

	@Test
	public void testGetY2() {
		assertEquals(withConstructor.getEastLon(), 4.4, 0.0);
	}

}
