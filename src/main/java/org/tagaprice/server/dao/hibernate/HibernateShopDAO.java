package org.tagaprice.server.dao.hibernate;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.tagaprice.core.entities.BasicShop;
import org.tagaprice.core.entities.ProductRevision;
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
	public Shop getById(long id) {
		return (Shop) _sessionFactory.getCurrentSession().load(Shop.class, id);
	}

	@Override
	public List<BasicShop> getByTitle(String title) {
		Criteria crit = _sessionFactory.getCurrentSession().createCriteria(BasicShop.class);
		crit.add(Restrictions.like("title", title, MatchMode.EXACT));
		crit.setMaxResults(10);

		return crit.list();
	}

	@Override
	public List<BasicShop> getByTitleFuzzy(String title) {
		Criteria crit = _sessionFactory.getCurrentSession().createCriteria(BasicShop.class);
		crit.add(Restrictions.ilike("title", title, MatchMode.ANYWHERE));
		crit.setMaxResults(10);

		return crit.list();
	}

	@Override
	public Shop save(Shop shop) {
		Session session = _sessionFactory.getCurrentSession();
		session.save(shop);
		return shop;
	}
}


