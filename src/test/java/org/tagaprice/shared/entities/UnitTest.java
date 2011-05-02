package org.tagaprice.shared.entities;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.shared.entities.accountmanagement.User;

public class UnitTest {

	Unit emptyUnit;
	Unit newUnit;
	Unit updateUnit;
	Unit setterUnit;
	
	User testUser = new User("testUID", "testRev", "Test User");

	@Before
	public void setUp() throws Exception {
		emptyUnit = new Unit();
		newUnit = new Unit(testUser, "title");
		updateUnit = new Unit(testUser, "1", "2", "title");
		setterUnit = new Unit();
	}

	@Test
	public void testUnit() {
		assertEquals(emptyUnit.getFactor(), 1.0, 0.0);
	}

	@Test
	public void testUnitString() {
		assertEquals(newUnit.getTitle(), "title");
		assertEquals(newUnit.getFactor(), 1.0, 0.0);
	}

	@Test
	public void testUnitStringStringString() {
		assertEquals(updateUnit.getTitle(), "title");
		assertEquals(updateUnit.getId(), "1");
		assertEquals(updateUnit.getRevision(), "2");
		assertEquals(updateUnit.getFactor(), 1.0, 0.0);
	}

	@Test
	public void testGetFactor() {
		assertEquals(updateUnit.getFactor(), 1.0, 0.0);
	}

	@Test
	public void testSetFactor() {
		setterUnit.setFactor(3.9);
		assertEquals(setterUnit.getFactor(), 3.9, 0.0);

	}

}
