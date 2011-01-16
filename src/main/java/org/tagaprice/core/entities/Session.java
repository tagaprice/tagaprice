package org.tagaprice.core.entities;

public class Session {

	private byte[] _sessionId;

	public Session(byte[] sessionId) {
		_sessionId = sessionId;
	}

	public byte[] getSessionId() {
		return _sessionId;
	}

}
