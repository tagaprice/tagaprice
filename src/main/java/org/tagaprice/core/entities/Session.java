package org.tagaprice.core.entities;

import java.util.Arrays;

/**
 * Represents a session token. Clients need to authenticate themselves, hereby obtaining a Session object which is used to make further requests.
 * @author "forste"
 *
 */
public class Session {

	private byte[] _sessionId;

	public Session(byte[] sessionId) {
		_sessionId = sessionId;
	}

	public byte[] getSessionId() {
		return _sessionId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(_sessionId);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Session other = (Session) obj;
		if (!Arrays.equals(_sessionId, other._sessionId))
			return false;
		return true;
	}
}
