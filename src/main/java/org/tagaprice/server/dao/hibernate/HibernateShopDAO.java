package org.tagaprice.server.dao.hibernate;

import org.hibernate.SessionFactory;
import org.tagaprice.core.entities.Shop;
import org.tagaprice.server.dao.interfaces.IShopDAO;

public class HibernateShopDAO implements IShopDAO {

	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	//	@Override
	//	public Product save(Product product) {
	//		Session session = _sessionFactory.getCurrentSession();
	//		session.save(product);
	//		return product;
	//	}

	@Override
	public Shop getShopById(long id) {
		return (Shop) _sessionFactory.getCurrentSession().load(Shop.class, id);
	}
}


