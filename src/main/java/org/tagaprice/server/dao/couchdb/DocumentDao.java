package org.tagaprice.server.dao.couchdb;

import org.tagaprice.shared.entities.Document;

public class DocumentDao extends DaoClass<Document> {
	CouchDbDaoFactory m_daoFactory;

	public DocumentDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, null, "document", null);
		m_daoFactory = daoFactory;
	}
}
