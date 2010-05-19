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
 * Filename: ApiVersion.java
 * Date: 19.05.2010
*/
package org.tagaprice.server;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class ApiManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ApiCall> calls = new HashMap<String, ApiCall>();
	
	protected void registerCall(ApiCall call) throws DuplicateNameException {
		if (calls.containsKey(call.getName()))
			throw new DuplicateNameException(call.getName());
		calls.put(call.getName(), call);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		/// TODO remove this test code!
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
		String callParts[] = req.getPathInfo().split("/");

		System.out.println("Part 0: "+callParts[0]);
		
		int i = 0;
		if (callParts[0].equals("")) i++;
		
		if (callParts.length < 2+i) {
			try {
				resp.sendError(400);
			} catch (IOException e) {/* ignored */}
			return;
		}
		String callName = callParts[i++];
		String function = callParts[i++];

		if (calls.containsKey(callName)) {
			/// TODO get output format and allocate the corresponding EntitySerializer
			ApiCall c = calls.get(callName);
			
			Responder responder = new Responder(new JsonSerializer());

			c.onCall(function, responder);
			try {
				responder.send(resp);
			} catch (IOException e) {
				try {
					resp.sendError(500);
				} catch (IOException e1) {/* ignored */}
			}
		}
		else {
			System.err.println("Unknown API call: "+callName);
			try {
				resp.sendError(404);
			} catch (IOException e) {/* ignore */}
		}
	}
}
