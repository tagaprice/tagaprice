package org.tagaprice.server.dao.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;


public class HibernateReceiptDAO implements IReceiptDAO {

	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ReceiptEntry> getReceiptEntriesByProductIdAndRev(Long productId, Integer rev) {
		Query q = _sessionFactory
		.getCurrentSession()
		.createQuery( /* TODO fix this query */
		"from ReceiptEntry as entry where entry.productId = :prodId and entry.productRevisionNumber  = :prodRev");
		q.setLong("prodId", productId);
		q.setInteger("prodRev", rev);

		return q.list();
	}

	@Override
	public Receipt save(Receipt receipt) {
		Session session = _sessionFactory.getCurrentSession();
		session.save(receipt);
		return receipt;
	}

}
