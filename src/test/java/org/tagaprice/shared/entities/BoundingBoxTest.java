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
		assertEquals(emptyBox.getX1(), 0.0, 0.0);
		assertEquals(emptyBox.getX2(), 0.0, 0.0);
		assertEquals(emptyBox.getY1(), 0.0, 0.0);
		assertEquals(emptyBox.getY2(), 0.0, 0.0);
	}


	@Test
	public void testGetX1() {
		assertEquals(withConstructor.getX1(), 1.1, 0.0);
	}

	@Test
	public void testGetY1() {
		assertEquals(withConstructor.getY1(), 2.2, 0.0);
	}

	@Test
	public void testGetX2() {
		assertEquals(withConstructor.getX2(), 3.3, 0.0);
	}

	@Test
	public void testGetY2() {
		assertEquals(withConstructor.getY2(), 4.4, 0.0);
	}

}
