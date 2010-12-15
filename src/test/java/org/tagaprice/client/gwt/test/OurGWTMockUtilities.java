package org.tagaprice.client.gwt.test;

import com.google.gwt.core.client.GWTBridge;
import com.google.gwt.core.client.GWT;

import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * This is almost an exact copy of GWT's com.google.gwt.junit.GWTMockUtilities except it uses our own GWTWidgetBridge
 */
public class OurGWTMockUtilities {
	public static void disarm() {
		GWTBridge bridge = new OurGWTBridge(); /** our change **/
		setGwtBridge(bridge);
	}

	public static void restore() {
		setGwtBridge(null);
	}

	private static void setGwtBridge(GWTBridge bridge) {
		Class gwtClass = GWT.class;
		Class[] paramTypes = new Class[] {GWTBridge.class};
		Method setBridgeMethod = null;
		try {
			setBridgeMethod = gwtClass.getDeclaredMethod("setBridge", paramTypes);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
		setBridgeMethod.setAccessible(true);
		try {
			setBridgeMethod.invoke(gwtClass, new Object[] {bridge});
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
}
