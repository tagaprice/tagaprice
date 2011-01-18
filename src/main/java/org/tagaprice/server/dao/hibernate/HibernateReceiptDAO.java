package org.tagaprice.server.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;


public class HibernateReceiptDAO implements IReceiptDAO {

	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ReceiptEntry> getReceiptEntriesByProductIdAndRev(long productId, int rev) {
		Query q = _sessionFactory
		.getCurrentSession()
		.createQuery( /* TODO fix this query */
		"from ReceiptEntry as entry where entry.productId = :prodId and entry.productRevisionNumber  = :prodRev");
		q.setString("prodId", String.valueOf(productId));
		q.setString("prodRev", String.valueOf(rev));

		return q.list();
	}

}
