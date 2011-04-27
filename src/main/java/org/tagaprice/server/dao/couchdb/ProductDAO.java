package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDAO;
import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IProductDAO;
import org.tagaprice.server.dao.IUnitDAO;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ProductDAO extends DAOClass<Product> implements IProductDAO {
	ICategoryDAO m_categoryDAO;
	IUnitDAO m_unitDAO;
	
	public ProductDAO(IDaoFactory daoFactory, String dbPrefix) {
		super(dbPrefix, Product.class, "product");
		m_categoryDAO = daoFactory.getCategoryDAO();
		m_unitDAO = daoFactory.getUnitDAO();
	}

	@Override
	public Product get(String id, String revision) throws DaoException {
		Product rc = super.get(id, revision);
		if (rc.getCategoryId() != null) {
			rc.setCategory(m_categoryDAO.get(rc.getCategoryId()));
		}
		
		if (rc.getUnitId() != null) {
			rc.setUnit(m_unitDAO.get(rc.getUnitId()));
		}
		return rc;
	}
	
	@Override
	public List<Product> find(Product searchPattern) throws DaoException {
		/// TODO implement actual searching here
		ViewResult<?> result = m_db.listDocuments(null, null);
		List<Product> rc = new ArrayList<Product>();
		
		for (ValueRow<?> row: result.getRows()) {
			Product product = get(row.getId());
			rc.add(product);
		}
		
		return rc;

	}
}
