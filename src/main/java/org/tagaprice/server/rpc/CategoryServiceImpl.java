package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CategoryServiceImpl extends RemoteServiceServlet implements ICategoryService {

	private static final long serialVersionUID = 1L;

	ICategoryDao productCategoryDAO;
	ICategoryDao shopCategoryDAO;

	public CategoryServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		productCategoryDAO = daoFactory.getProductCategoryDao();
		shopCategoryDAO = daoFactory.getShopCategoryDao();
	}

	@Override
	public Category getProductCategory(String id, String revision) throws DaoException {
		return productCategoryDAO.get(id, revision);
	}

	@Override
	public Category getProductCategory(String id) throws DaoException {
		return productCategoryDAO.get(id);
	}

	@Override
	public List<Category> getProductCategoryChildren(String id) throws DaoException {
		return productCategoryDAO.getChildren(id);
	}

	@Override
	public Category getShopCategory(String id, String revision)
			throws DaoException {
		return shopCategoryDAO.get(id, revision);
	}

	@Override
	public Category getShopCategory(String id) throws DaoException {
		return shopCategoryDAO.get(id);
	}

	@Override
	public List<Category> getShopCategoryChildren(String id)
			throws DaoException {
		return shopCategoryDAO.getChildren(id);
	}

}
