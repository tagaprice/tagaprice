package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ProductDAO extends DAOClass<Product> implements IProductDAO {
	ICategoryDAO m_categoryDAO;
	IUnitDAO m_unitDAO;
	
	public ProductDAO(CouchDBDaoFactory daoFactory) {
		super(Product.class, "product", daoFactory._getEntityDao());
		m_categoryDAO = daoFactory.getCategoryDAO();
		m_unitDAO = daoFactory.getUnitDAO();
	}

	@Override
	public List<Product> find(Product searchPattern) throws DaoException {
		/// TODO implement actual searching here
		ViewResult<?> result = m_db.queryView("product/all", Product.class, null, null);
		List<Product> rc = new ArrayList<Product>();
		
		for (ValueRow<?> row: result.getRows()) {
			Product product = get(row.getId());
			rc.add(product);
		}
		
		return rc;

	}
	
	protected void _injectFields(Product product) throws DaoException {
		if (product.getCategoryId() != null) {
			product.setCategory(m_categoryDAO.get(product.getCategoryId()));
		}
		
		if (product.getUnitId() != null) {
			product.setUnit(m_unitDAO.get(product.getUnitId()));
		}
	}
}
