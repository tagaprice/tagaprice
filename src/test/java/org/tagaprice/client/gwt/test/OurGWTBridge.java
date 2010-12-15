package org.tagaprice.client.gwt.test;

import com.google.gwt.core.client.GWTBridge;
import static org.mockito.Mockito.*;
/*
 * Found on http://www.assertinteresting.com/2009/05/unit-testing-gwt/
 */

/**
 * This is an exact copy of com.google.gwt.junit.GWTDummyBridge except
 * it returns mocked Widgets instead of null's
 **/
public class OurGWTBridge<T> extends GWTBridge {
	@Override
	public  T create(Class classLiteral) {
		return (T) mock(classLiteral); /** Mock what we create.  This used to return null. **/
	}

	@Override
	public String getVersion() {
		return "0";
	}

	@Override
	public boolean isClient() {
		return false;
	}

	@Override
	public void log(String s, Throwable throwable) {
		System.out.println(s);
	}
}
