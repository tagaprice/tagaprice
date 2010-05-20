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
 * Filename: Responder.java
 * Date: 19.05.2010
*/
package org.tagaprice.server;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.tagaprice.shared.Serializable;
import org.tagaprice.shared.ServerResponse;

public class Responder extends ServerResponse {
	private EntitySerializer serializer;
	
	public Responder(EntitySerializer serializer) {
		super(StatusCode.Ok, null);
		
		this.serializer = serializer;
	}
	
	public void setStatusCode(ServerResponse.StatusCode code) {
		status = code;
	}
	
	public void setResponse(Serializable r) {
		response = r;
	}
	
	public void send(HttpServletResponse resp) throws IOException {
		//resp.setCharacterEncoding("utf-8"); /// TODO find out why that doesn't work
		resp.setContentType("text/plain; charset=\"utf-8\""); /// TODO use better content types (e.g. application/json)

		serializer.put(this);
	}
}
