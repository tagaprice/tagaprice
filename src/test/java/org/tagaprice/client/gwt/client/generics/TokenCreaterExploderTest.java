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
	public void testEscapedSlashExplosion() {
		String testString = "/theRoot/the%2FVarName/the%2FValue";
		exploder = TokenCreator.getExploder(testString);
		Assert.assertEquals("the/Value", exploder.getNode("the/VarName"));
	}

	/**
	 * This test asumes, that a value uses the separator char (/) itself...
	 */
	@Test
	public void testEscapedPercentExplosion() {
		String testString = "/theRoot/the%2FVarName/the%2FValue/the%2FVarNameWith%25/the%2FValueWith%25";
		exploder = TokenCreator.getExploder(testString);
		Assert.assertEquals("the/Value", exploder.getNode("the/VarName"));
		Assert.assertEquals("the/ValueWith%", exploder.getNode("the/VarNameWith%"));
	}

}
