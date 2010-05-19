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

import org.tagaprice.shared.Entity;
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
	
	public void setEntity(Entity e) {
		entity = e;
	}
	
	public void send(HttpServletResponse resp) throws IOException {
		StringBuffer data = new StringBuffer();
		data = serializer.put(this);
		
		resp.setContentType("text/plain"); /// TODO use better content types (e.g. application/json)
		resp.setCharacterEncoding("utf-8");
		resp.getOutputStream().write(data.toString().getBytes());
	}
}
