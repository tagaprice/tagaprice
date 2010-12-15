package org.tagaprice.server.dao.hibernate;

import org.hibernate.SessionFactory;
import org.tagaprice.core.entities.Product;
import org.tagaprice.server.dao.interfaces.IProductDAO;

/** TODO proper setup through spring beans config */
public class HibernateProductDAO implements IProductDAO {

	/** TODO @Autowired */
	private SessionFactory _sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		_sessionFactory = sessionFactory;
	}

	@Override
	//	@Transactional
	/** TODO remove @Transactional from this method! if removed, tests aren't working. find a way to setup tests correctly */
	public Product save(Product product) {
		_sessionFactory.getCurrentSession().save(product);
		return product;
	}

}


