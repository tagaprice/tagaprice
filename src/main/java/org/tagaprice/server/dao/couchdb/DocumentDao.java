package org.tagaprice.server.dao.couchdb;

import org.tagaprice.shared.entities.ADocument;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class DocumentDao extends DaoClass<ADocument> {
	CouchDbDaoFactory m_daoFactory;
	UserDao m_userDao = null;
	public DocumentDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, null, "document", null);
		m_daoFactory = daoFactory;
	}

	@Override
	protected void _injectFields(ADocument document) throws DaoException {
		if (m_userDao == null) {
			// m_userDao initialization (can't be done in the constructor (endless recursion))
			m_userDao = m_daoFactory.getUserDao();
		}
		if (document.getCreatorId() != null) {
			document.setCreator(m_userDao.get(document.getCreatorId()));
		}
	}
}
