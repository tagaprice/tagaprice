package org.tagaprice.shared.logging;

public class MyLoggerImpl implements MyLogger {
	@SuppressWarnings("rawtypes")
	private Class theClass;

	public MyLoggerImpl(@SuppressWarnings("rawtypes") Class theClass) {
		this.theClass = theClass;
		log("Logger started");
	}

	@Override
	public void log(String message) {
		System.out.println("Class: " + this.theClass.getName() + " Log: "
				+ message);
	}

}
