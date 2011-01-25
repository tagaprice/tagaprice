package org.tagaprice.server.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
import org.tagaprice.core.entities.Product;
import org.tagaprice.core.entities.ProductRevision;
import org.tagaprice.server.dao.interfaces.IProductDAO;

public class HibernateProductDAO implements IProductDAO {

	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	public Product save(Product product) {
		// set all ids in ProductRevisions to the same id as Product
//		for(ProductRevision rev : product.getRevisions()) {
//			rev.setId(product.getId());
//		}

		Session session = _sessionFactory.getCurrentSession();
		session.saveOrUpdate(product);
		return product;
	}

	@Override
	public Product getById(Long id) {
		return (Product) _sessionFactory.getCurrentSession().get(Product.class, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Product> getAll() {
		return _sessionFactory.getCurrentSession().createQuery("from Product").list();
	}

	@Override
	public int countAll() {
		Criteria criteria = _sessionFactory.getCurrentSession().createCriteria(Product.class);
		criteria.setProjection(Projections.rowCount());
		return ((Long)criteria.list().get(0)).intValue();
	}
}


