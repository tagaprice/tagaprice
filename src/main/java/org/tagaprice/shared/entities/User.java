package org.tagaprice.shared.entities;

import org.svenson.JSONProperty;

public class User {
	private String m_id; /// DB-internal identifier

	/**
	 * DB-internal revision number
	 * 
	 * This revision number is necessary to handle revision concurrency
	 * Therefore it's mandatory to send a revision when trying to store a new User/Entity/Whatever
	 */
	private String m_rev;
	private String m_mail; ///
	private String m_name;
	
	@JSONProperty(value="_id")
	public String getId() {
		return m_id;
	}

	@JSONProperty(value="_rev")
	public String getRev() {
		return m_rev;
	}
	
	/// Return a descriptive user name-
	@JSONProperty(value="name")
	public String getName() {
		return m_name;
	}
}
