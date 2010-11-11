/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: tagaprice
 * Filename: Serializable.java
 * Date: 20.05.2010
*/
package org.tagaprice.shared;

/**
 * Everything that can be de-/serialized with EntitySerializer must implement this interface.
 * @author Manuel Reithuber
 *
 */
public interface Serializable extends java.io.Serializable, com.google.gwt.user.client.rpc.IsSerializable {
	public String getSerializeName();
}
