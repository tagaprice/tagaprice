package org.tagaprice.server.dao.couchdb;

import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.shared.entities.accountmanagement.Session;

public class SessionDao extends DaoClass<Session> implements ISessionDao {
	public SessionDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Session.class, "session", daoFactory._getEntityDao());
	}
}
