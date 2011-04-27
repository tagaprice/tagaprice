package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.exceptions.dao.DaoException;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.categorymanagement.ICategoryService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class CategoryServiceImpl extends RemoteServiceServlet implements ICategoryService {

	private static final long serialVersionUID = 1L;

	MyLogger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);
	ICategoryDAO categoryDAO;

	public CategoryServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		categoryDAO = daoFactory.getCategoryDAO();
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
	public List<Category> getCategoryChildren(String id) {
		return categoryDAO.children(id);
	}

}
