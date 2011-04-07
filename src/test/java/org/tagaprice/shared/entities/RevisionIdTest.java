package org.tagaprice.shared.entities;


import org.junit.*;
import org.tagaprice.shared.entities.RevisionId;

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
		this.underTest = new RevisionId("539", "432");
		this.equalRevision = new RevisionId("539", "432");
		this.differentRevision = new RevisionId("123", "321");
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

}
