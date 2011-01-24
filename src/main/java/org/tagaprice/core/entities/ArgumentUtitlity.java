package org.tagaprice.core.entities;

public class ArgumentUtitlity {
	/**
	 * Throws an {@link IllegalArgumentException} if given obj is null. 
	 * @param name Exception message will be formated as "name must not be null"
	 * @param obj
	 */
	public static void checkNull(String name, Object obj) {
		if(obj == null)
			throw new IllegalArgumentException(name+" must not be null");
	}
}
