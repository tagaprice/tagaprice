package org.tagaprice.shared.logging;

public class LoggerFactory {
	@SuppressWarnings("rawtypes")
	public static MyLogger getLogger(Class theClass) {
		return new MyLoggerImpl(theClass);
	}

}
