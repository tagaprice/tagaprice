package org.tagaprice.server.dao.couchdb;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.accountmanagement.Session;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class SessionDao extends DaoClass<Session> implements ISessionDao {
	SimpleDateFormat m_dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	public SessionDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Session.class, Document.Type.SESSION, null);
	}
	
	@Override
	public Session get(String sessionId) throws DaoException {
		Session rc = super.get(sessionId);
		
		try {
			rc.setExpirationDate(m_dateFormatter.parse(rc.getExpirationDateString()));
		} catch (ParseException e) {
			throw new DaoException("Date format error", e);
		}
		
		return rc;
	}
	
	@Override
	public Session create(Session session) throws DaoException {
		session.setExpirationDateString(m_dateFormatter.format(session.getExpirationDate()));
		return super.create(session);
	}
	
	@Override
	public Session update(Session session) throws DaoException {		
		session.setExpirationDateString(m_dateFormatter.format(session.getExpirationDate()));
		return super.create(session);
	}
}
