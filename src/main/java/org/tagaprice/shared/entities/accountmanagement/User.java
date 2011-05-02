package org.tagaprice.shared.entities.accountmanagement;

import org.tagaprice.shared.entities.AEntity;

/**
 * Simple class that represents any kind of entity-creating user.
 * A User is identified by it's UID (see {@link AEntity.getId()}.
 * The UI will display the user name returned by {@link ASimpleEntity.getId()}
 */
public class User extends AEntity {
	private static final long serialVersionUID = 1L;

	public User() {
		// TODO Auto-generated constructor stub
	}


}
