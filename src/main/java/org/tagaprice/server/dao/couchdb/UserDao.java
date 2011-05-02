package org.tagaprice.server.dao.couchdb;

import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class UserDao extends DaoClass<User> {
	public UserDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, User.class, "user", daoFactory._getEntityDao());
	}
	
	@Override
	protected void _checkCreatorId(String creatorId) throws DaoException {
		if (creatorId != null) throw new DaoException("The creator ID of User objects has to be null!");
	}
}
