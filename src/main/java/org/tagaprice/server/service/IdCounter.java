package org.tagaprice.server.service;

public class IdCounter {
	private static Long _id = 0L;

	public synchronized static Long getNewId() {
		IdCounter._id++;
		return IdCounter._id;
	}

}
