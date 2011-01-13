package org.tagaprice.server.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.classic.Session;
import org.hibernate.criterion.Projections;
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
	/** TODO remove @Transactional from this method! if removed, tests aren't working. find a way to setup tests correctly */
	public Product save(Product product) {
		Session session = _sessionFactory.getCurrentSession();
		//		session.beginTransaction();
		session.save(product);
		//		session.getTransaction().commit();
		return product;
	}

	@Override
	public Product getById(Long id) {
		//		ProductRevision key = new ProductRevision();
		//
		//		ReflectionTestUtils.invokeSetterMethod(key, "setId", id);
		//		ReflectionTestUtils.invokeSetterMethod(key, "setRevisionNumber",revision);

		//		_sessionFactory.getCurrentSession().get
		return (Product) _sessionFactory.getCurrentSession().load(Product.class, id);
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


