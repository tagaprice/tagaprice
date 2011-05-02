package org.tagaprice.server.dao.couchdb;

import org.tagaprice.shared.entities.ASimpleEntity;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class EntityDao extends DAOClass<ASimpleEntity> {
	CouchDBDaoFactory m_daoFactory;
	UserDao m_userDao = null;
	public EntityDao(CouchDBDaoFactory daoFactory) {
		super(null, "entity", null);
		m_daoFactory = daoFactory;
	}

	protected void _injectFields(ASimpleEntity entity) throws DaoException {
		if (m_userDao == null) {
			// m_userDao initialization (can't be done in the constructor (endless recursion))
			m_userDao = m_daoFactory.getUserDao();
		}
		if (entity.getCreatorId() != null) {
			entity.setCreator(m_userDao.get(entity.getCreatorId()));
		}
	}
}
