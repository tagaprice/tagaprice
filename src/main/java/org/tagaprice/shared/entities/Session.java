package org.tagaprice.shared.entities;

/**
 * Represents a session for a user. A user may have multiple (active) sessions, for each session a Session Object with a unique session id will be created.
 * @author "forste"
 */
public class Session {
	private final String _sid;
	private final long _uid;

	public Session(String sid, long uid) {
		_sid = sid;
		_uid = uid;
	}

	/**
	 * Copy constructor. Performs a deep copy.
	 * @param session
	 */
	public Session(Session session) {
		_uid = session.getUserId();
		_sid = session.getSessionId();
	}


	public long getUserId() {
		return _uid;
	}
	public String getSessionId() {
		return _sid;
	}
}
