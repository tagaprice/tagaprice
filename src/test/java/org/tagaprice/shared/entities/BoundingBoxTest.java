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
		assertEquals(emptyBox.getSouthWestLat(), 0.0, 0.0);
		assertEquals(emptyBox.getNorthEastLat(), 0.0, 0.0);
		assertEquals(emptyBox.getSouthWestLon(), 0.0, 0.0);
		assertEquals(emptyBox.getNorthEastLon(), 0.0, 0.0);
	}


	@Test
	public void testGetX1() {
		assertEquals(withConstructor.getSouthWestLat(), 1.1, 0.0);
	}

	@Test
	public void testGetY1() {
		assertEquals(withConstructor.getSouthWestLon(), 2.2, 0.0);
	}

	@Test
	public void testGetX2() {
		assertEquals(withConstructor.getNorthEastLat(), 3.3, 0.0);
	}

	@Test
	public void testGetY2() {
		assertEquals(withConstructor.getNorthEastLon(), 4.4, 0.0);
	}

}
