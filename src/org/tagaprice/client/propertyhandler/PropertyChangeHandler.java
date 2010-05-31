/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: PropertyChangeHandler.java
 * Date: 31.05.2010
*/
package org.tagaprice.client.propertyhandler;


public interface PropertyChangeHandler {
	
	/**
	 * 
	 * @param errorType
	 */
	public void onError();
	
	/**
	 * 
	 * @param errorType
	 */
	public void onSuccess();
}
