package org.tagaprice.server.service;

public class IdCounter {
	private static Long _id = 4L; // TODO fix initialization


	public synchronized static Long getNewId() {
		IdCounter._id++;
		return IdCounter._id;
	}

}
