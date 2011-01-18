package org.tagaprice.server.dao.hibernate;

import java.util.SortedSet;

import org.hibernate.SessionFactory;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;

@SuppressWarnings("unused")
public class HibernateReceiptDAO implements IReceiptDAO {

	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	public SortedSet<ReceiptEntry> getReceiptEntriesByProductIdAndRev(long productId, int rev) {
		// TODO Auto-generated method stub
		return null;
	}

}


