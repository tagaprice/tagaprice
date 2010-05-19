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

public abstract class ApiVersion extends HttpServlet {
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
		String callName = req.getPathInfo();
		if (callName.substring(0, 1).equals("/")) callName = callName.substring(1);

		if (calls.containsKey(callName)) {
			/// TODO parse call and pass it on to the corresponding ApiCall object
			System.out.println("API call: "+callName); 
		}
		else {
			System.err.println("Unknown API call: "+callName);
			try {
				resp.sendError(404);
			} catch (IOException e) {/* ignore */}
		}
	}
}
