package org.tagaprice.server.dao;

import org.tagaprice.shared.entities.accountmanagement.User;

public interface IUserDao extends IDaoClass<User> {
	/**
	 * Returns a User object for a specific mail address.
	 * @param mail Mail address 
	 * @return User object (if the mail address wasn't found in the database, null is returned)
	 */
	User getByMail(String mail);
}
