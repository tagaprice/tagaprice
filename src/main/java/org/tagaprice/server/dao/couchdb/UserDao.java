package org.tagaprice.server.dao.couchdb;

import org.tagaprice.shared.entities.accountmanagement.User;

public class UserDao extends DaoClass<User> {
	public UserDao(CouchDbDaoFactory daoFactory) {
		super(User.class, "user", daoFactory._getEntityDao());
	}
}
