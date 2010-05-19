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
 * Filename: ApiCall.java
 * Date: 19.05.2010
*/
package org.tagaprice.server;

public interface ApiCall {
	public String getName();
	
	public void onCall(String function, Responder r);
}
