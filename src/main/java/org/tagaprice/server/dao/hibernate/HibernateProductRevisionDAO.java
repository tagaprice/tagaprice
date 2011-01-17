package org.tagaprice.server.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.interfaces.IProductRevisionDAO;

public class HibernateProductRevisionDAO implements IProductRevisionDAO {


	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ProductRevision> getByTitle(String title) {
		Criteria crit = _sessionFactory.getCurrentSession().createCriteria(ProductRevision.class);

		crit.add( Restrictions.like( "title", title));

		crit.setMaxResults(10);

		return crit.list();
	}


}
