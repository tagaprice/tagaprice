package org.tagaprice.server.dao.hibernate;

import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.tagaprice.core.entities.Category;
import org.tagaprice.core.entities.Product;
import org.tagaprice.server.dao.interfaces.ICategoryDAO;

public class HibernateCategoryDAO implements ICategoryDAO {

	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	public Category save(Category category) {
		Session session = _sessionFactory.getCurrentSession();
		session.save(category);
		return category;
	}

}
