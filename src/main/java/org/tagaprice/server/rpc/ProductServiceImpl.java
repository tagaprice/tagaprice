package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.ICategory;
import org.tagaprice.shared.entities.productmanagement.IPackage;
import org.tagaprice.shared.entities.productmanagement.IProduct;
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
	public IProduct getProduct(IRevisionId revionsId) {
		return productDAO.get(revionsId);
	}

	@Override
	public IProduct saveProduct(final IProduct product) {
		return productDAO.update(product);
	}

	@Override
	public List<IProduct> findProducts(IProduct searchCriteria) {
		logger.log("findProducts... searchCriteria: " + searchCriteria);
		return productDAO.find(searchCriteria);
	}

	@Override
	public List<ICategory> getCategories() {
		logger.log("return categories");
		return categoryDAO.list();
	}


	@Override
	public IPackage getPackage(IRevisionId revisionId) {
		return packageDAO.get(revisionId);
	}


	@Override
	public IPackage savePackage(IPackage pkg) {
		return packageDAO.update(pkg);
	}


	@Override
	public List<ICategory> getCategoryChilds(IRevisionId revisionId) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public ICategory getCategory(IRevisionId revisionId) {
		// TODO Auto-generated method stub
		return null;
	}
}
