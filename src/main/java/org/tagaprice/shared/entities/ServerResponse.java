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
package org.tagaprice.shared.entities;

import org.tagaprice.shared.ISerializable;

//TODO unused, what is this for ?
public class ServerResponse implements ISerializable {
	private static final long serialVersionUID = 1L;

	public enum StatusCode {
		Ok(0),
		AccessDenied(1),
		NotFound(2),
		RequestError(3);
		/// TODO fill this list with useful data

		private int value;

		private StatusCode(int code) {
			value = code;
		}

		public static StatusCode getByName(String name) {
			StatusCode rc = null;
			if (name == null) {}
			else if (name.equals("ok")) rc = Ok;
			else if (name.equals("accessDenied")) rc = AccessDenied;
			else if (name.equals("notFound")) rc = NotFound;
			else if (name.equals("requestError")) rc = RequestError;

			return rc;
		}

		public String getName() {
			String rc = null;
			if (value == Ok.value) rc = "ok";
			else if (value == AccessDenied.value) rc = "accessDenied";
			else if (value == NotFound.value) rc = "notFound";
			else if (value == RequestError.value) rc = "requestError";
			return rc;
		}
	}

	protected StatusCode status;
	protected ISerializable response;

	public ServerResponse(StatusCode status, ISerializable response) {
		this.status = status;
		this.response = response;
	}

	public StatusCode getStatus() {
		return status;
	}

	public ISerializable getResponse() {
		return response;
	}

	public String getStatusName() {
		return status != null ? status.getName() : null;
	}

	@Override
	public String getSerializeName() {
		return "serverResponse";
	}

	@Override
	public boolean equals(Object o) {
		boolean rc = true;

		if (o instanceof ServerResponse) {
			ServerResponse r = (ServerResponse) o;
			if (getStatus() == null) {
				if (r.getStatus() != null) rc = false;
			}
			else if (!getStatus().equals(r.getStatus())) rc = false;
			if (getResponse() == null) {
				if (r.getResponse() != null) rc = false;
			}
			else if (!getResponse().equals(r.getResponse())) rc = false;
		}
		else rc = false;

		return rc;
	}
}
