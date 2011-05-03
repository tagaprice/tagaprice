package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.shared.entities.accountmanagement.Session;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.productmanagement.IProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements IProductService {
	private static final long serialVersionUID = 1L;

	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	IPackageDao packageDAO;
	IProductDao productDAO;
	ICategoryDao categoryDAO;
	ISessionDao sessionDAO;

	/**
	 * Initialization with some products and Categories.
	 */
	public ProductServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		packageDAO = daoFactory.getPackageDao();
		productDAO = daoFactory.getProductDao();
		categoryDAO = daoFactory.getCategoryDao();
		sessionDAO = daoFactory.getSessionDao();
	}


	@Override
	public Product getProduct(String id, String revision) throws DaoException {
		return productDAO.get(id, revision);
	}

	@Override
	public Product getProduct(String id) throws DaoException {
		return productDAO.get(id);
	}

	@Override
	public Product saveProduct(String sessionId, final Product product) throws DaoException {
		logger.log("save Product " + product);
		Session session = sessionDAO.get(sessionId);
		Product rc = null;
		
		// TODO check session validity
		product.setCreator(session.getCreator());
		
		if(product.getId() != null){
			rc = productDAO.update(product);
		}else{
			rc = productDAO.create(product);
		}
		return rc;
	}

	@Override
	public List<Product> findProducts(Product searchCriteria) throws DaoException {
		logger.log("findProducts... searchCriteria: " + searchCriteria);
		return productDAO.find(searchCriteria);
	}

	@Override
	public List<Category> getCategories() throws DaoException {
		logger.log("return categories");
		return categoryDAO.list();
	}


	@Override
	public Package getPackage(String id, String revision) throws DaoException {
		return packageDAO.get(id, revision);
	}

	@Override
	public Package getPackage(String id) throws DaoException {
		return packageDAO.get(id);
	}


	@Override
	public Package savePackage(Package pkg) throws DaoException {
		return packageDAO.update(pkg);
	}


	@Override
	public List<Category> getCategoryChildren(String id) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public Category getCategory(String id) {
		// TODO Auto-generated method stub
		return null;
	}
}
