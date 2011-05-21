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

	ICategoryDao categoryDAO;

	public CategoryServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		categoryDAO = daoFactory.getCategoryDao();
	}

	@Override
	public Category getCategory(String id, String revision) throws DaoException {
		return categoryDAO.get(id, revision);
	}

	@Override
	public Category getCategory(String id) throws DaoException {
		return categoryDAO.get(id);
	}

	@Override
	public List<Category> getCategoryChildren(String id) throws DaoException {
		return categoryDAO.getChildren(id);
	}

}
