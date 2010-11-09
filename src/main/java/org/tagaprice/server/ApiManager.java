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

import org.tagaprice.server.serializer.JsonSerializer;
import org.tagaprice.shared.RequestError;
import org.tagaprice.shared.ServerResponse.StatusCode;
import org.tagaprice.shared.exception.RequestException;

public abstract class ApiManager extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private HashMap<String, ApiCall> calls = new HashMap<String, ApiCall>();
	
	protected void registerCall(ApiCall call) throws DuplicateNameException {
		if (calls.containsKey(call.getName()))
			throw new DuplicateNameException(call.getName());
		calls.put(call.getName(), call);
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		/// TODO remove this test code!
		doPost(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String callParts[] = req.getPathInfo().split("/");

		int i = 0;
		if (callParts[0].equals("")) i++;
		
		if (callParts.length < 2+i) {
			resp.sendError(400);
			return;
		}
		String callName = callParts[i++];
		String function = callParts[i++];

		if (calls.containsKey(callName)) {
			/// TODO get output format and allocate the corresponding EntitySerializer
			ApiCall c = calls.get(callName);
			
			ApiCallData responder = new ApiCallData(req, new JsonSerializer(resp.getOutputStream()));

			try {
				c.onCall(function, responder);
			}
			catch (RequestException e) {
				responder.setStatusCode(StatusCode.RequestError);
				responder.setResponse(new RequestError(e.getMessage()));
			}
			
			responder.send(resp);
		}
		else {
			System.err.println("Unknown API call: "+callName);
			resp.sendError(404);
		}
	}
}
