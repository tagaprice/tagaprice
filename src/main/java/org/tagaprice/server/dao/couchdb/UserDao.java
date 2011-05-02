package org.tagaprice.server.dao.couchdb;

import org.tagaprice.shared.entities.accountmanagement.User;

public class UserDao extends DAOClass<User> {
	public UserDao(CouchDBDaoFactory daoFactory) {
		super(User.class, "user", daoFactory._getEntityDao());
	}
}
