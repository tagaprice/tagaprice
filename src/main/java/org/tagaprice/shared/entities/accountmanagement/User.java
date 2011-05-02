package org.tagaprice.shared.entities.accountmanagement;

import org.tagaprice.shared.entities.AEntity;

/**
 * Simple class that represents any kind of entity-creating user.
 * A User is identified by it's UID (see {@link AEntity.getId()}.
 * The UI will display the user name returned by {@link ASimpleEntity.getId()}
 */
public class User extends AEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constuctor (necessary for serialization)
	 */
	public User() {
		super();
	}
	
	/**
	 * Constructor for new User instances
	 * @param title Descriptive User name (e.g. "John Doe")
	 */
	public User(String title) {
		super(null, title);
	}

	/**
	 * Constructor for User instances queried from the database
	 * @param uid User ID
	 * @param revision Entity revision
	 * @param title Descriptive User name (e.g. "John Doe")
	 */
	public User(String uid, String revision, String title) {
		super(null, uid, revision, title);
	}
}
