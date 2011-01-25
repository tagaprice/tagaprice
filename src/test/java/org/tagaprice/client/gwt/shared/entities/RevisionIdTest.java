package org.tagaprice.client.gwt.shared.entities;


import org.junit.*;

public class RevisionIdTest {
	RevisionId underTest;
	Object equalRevision;
	Object differentRevision;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.underTest = new RevisionId(539L, 432);
		this.equalRevision = new RevisionId(539L, 432);
		this.differentRevision = new RevisionId(123L, 321);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEqualsShouldReturnTrue() {
		Assert.assertEquals(true, this.underTest.equals(equalRevision));
		Assert.assertEquals(true, equalRevision.equals(underTest));
	}

	@Test
	public void testEqualsShouldReturnFalse() {
		Assert.assertEquals(false, this.underTest.equals(differentRevision));
		Assert.assertEquals(false, differentRevision.equals(this.underTest));
	}

	@Test
	public void testToString() {
		Assert.assertEquals("539_432", this.underTest.toString());
		Assert.assertEquals("539_432", this.equalRevision.toString());
	}

	@Test
	public void testHashCode() {
		Assert.assertEquals(20375, this.underTest.hashCode());
		Assert.assertEquals(20375, this.equalRevision.hashCode());
		Assert.assertEquals(4872, this.differentRevision.hashCode());
	}

}
