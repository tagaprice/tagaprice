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
 * Filename: TaPResponse.java
 * Date: 19.05.2010
*/
package org.tagaprice.shared;

public class ServerResponse {
	public enum StatusCode {
		Ok,
		AccessDenied,
		NotFound,
		/// TODO fill this list with useful data
	}
	
	protected StatusCode status;
	protected Serializable response;
	
	public ServerResponse(StatusCode status, Serializable response) {
		this.status = status;
		this.response = response;
	}
	
	public StatusCode getStatus() {
		return status;
	}
	
	public Serializable getResponse() {
		return response;
	}
	
	public String getStatusName() {
		String rc = null;
		
		if (status == StatusCode.Ok) rc = "ok";
		else if (status == StatusCode.AccessDenied) rc = "accessdenied";
		else if (status == StatusCode.NotFound) rc = "notfound";
		
		return rc;
	}
}
