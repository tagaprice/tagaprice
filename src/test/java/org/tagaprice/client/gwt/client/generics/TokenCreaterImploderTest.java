package org.tagaprice.client.gwt.client.generics;


import org.junit.*;
import org.tagaprice.client.gwt.client.generics.TokenCreator.Imploder;

public class TokenCreaterImploderTest {
	Imploder imploder;

	@Before
	public void setUp() throws Exception {
		imploder = TokenCreator.getImploder();
	}

	@After
	public void tearDown() throws Exception {
		imploder = null;
	}

	@Test
	public void testNormalImplosion() {
		imploder.setRoot("theRoot");
		Assert.assertEquals("/theRoot", imploder.getToken());
		imploder.addNode("varName", "varValue");
		Assert.assertEquals("/theRoot/varName/varValue", imploder.getToken());
		imploder.addNode("anotherValue", "12345");
		Assert.assertEquals("/theRoot/varName/varValue/anotherValue/12345", imploder.getToken());
	}

	@Test
	public void testEscapeImplotion() {
		imploder.setRoot("theRoot");
		imploder.addNode("the/VarName", "the/Value");
		Assert.assertEquals("/theRoot/the%2FVarName/the%2FValue", imploder.getToken());
		imploder.addNode("the/VarNameWith%", "the/ValueWith%");
		Assert.assertEquals("/theRoot/the%2FVarName/the%2FValue/the%2FVarNameWith%25/the%2FValueWith%25", imploder.getToken());
	}
}
