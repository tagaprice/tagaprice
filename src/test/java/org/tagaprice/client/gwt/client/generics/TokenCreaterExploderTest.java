package org.tagaprice.client.gwt.client.generics;


import org.junit.*;

public class TokenCreaterExploderTest {
	TokenCreator.Exploder exploder;

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
		exploder = null;
	}

	@Test
	public void testNormalExplosion() {
		String testString = "/theRoot/varName/varValue";
		exploder = TokenCreator.getExploder(testString);
		Assert.assertEquals("theRoot", exploder.getRoot());
		Assert.assertEquals("varValue", exploder.getNode("varName"));
	}

	/**
	 * This test asumes, that a value uses the separator char (/) itself...
	 */
	@Test
	public void testEscapedExplosion() {
		String testString = "/theRoot/varName/var/Value";
		exploder = TokenCreator.getExploder(testString);
	}

}
