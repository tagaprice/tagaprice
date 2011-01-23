package org.tagaprice.server.helper;

public class ArgumentUtitlity {
	public static void checkNull(String name, Object obj) {
		if(obj == null)
			throw new IllegalArgumentException(name+" must not be null");
	}
}
