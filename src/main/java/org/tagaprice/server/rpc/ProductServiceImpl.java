package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.productmanagement.IProductService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ProductServiceImpl extends RemoteServiceServlet implements IProductService {
	private static final long serialVersionUID = 1L;

	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	IPackageDAO packageDAO;
	IProductDAO productDAO;
	ICategoryDAO categoryDAO;

	/**
	 * Initialization with some products and Categories.
	 */
	public ProductServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		packageDAO = daoFactory.getPackageDAO();
		productDAO = daoFactory.getProductDAO();
		categoryDAO = daoFactory.getCategoryDAO();
	}


	@Override
	public Product getProduct(String id, String revision) {
		return productDAO.get(id, revision);
	}

	@Override
	public Product getProduct(String id) {
		return productDAO.get(id);
	}

	@Override
	public Product saveProduct(final Product product) {
		logger.log("save Product " + product);
		Product rc = null;
		if(product.getId() != null){
			rc = productDAO.update(product);
		}else{
			rc = productDAO.create(product);
		}
		return rc;
	}

	@Override
	public List<Product> findProducts(Product searchCriteria) {
		logger.log("findProducts... searchCriteria: " + searchCriteria);
		return productDAO.find(searchCriteria);
	}

	@Override
	public List<Category> getCategories() {
		logger.log("return categories");
		return categoryDAO.list();
	}


	@Override
	public Package getPackage(String id, String revision) {
		return packageDAO.get(id, revision);
	}

	@Override
	public Package getPackage(String id) {
		return packageDAO.get(id);
	}


	@Override
	public Package savePackage(Package pkg) {
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
