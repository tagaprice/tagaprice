package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IProductDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ProductDao extends DaoClass<Product> implements IProductDao {
	ICategoryDao m_categoryDAO;
	IUnitDao m_unitDAO;
	IPackageDao m_packageDAO;

	public ProductDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Product.class, "product", daoFactory._getDocumentDao());
		m_categoryDAO = daoFactory.getProductCategoryDao();
		m_unitDAO = daoFactory.getUnitDao();
		m_packageDAO = daoFactory.getPackageDao();
	}



	@Override
	public Product create(Product product) throws DaoException {
		Product rc = super.create(product);

		//create Packages
		ArrayList<Package> pl = new ArrayList<Package>();
		for(Package p: rc.getPackages()){
			p.setCreator(rc.getCreator());
			pl.add(m_packageDAO.create(p));
		}
		rc.setPackages(pl);

		return rc;
	}

	@Override
	public Product update(Product product) throws DaoException {
		Product rc = super.update(product);

		//create Packages
		ArrayList<Package> pl = new ArrayList<Package>();
		for(Package p: rc.getPackages()){
			if(p.getCreator()==null){
				p.setCreator(rc.getCreator());
				pl.add(m_packageDAO.create(p));
			}else{
				p.setCreator(rc.getCreator());
				pl.add(m_packageDAO.update(p));
			}
		}
		rc.setPackages(pl);

		return rc;
	}



	@Override
	protected void _injectFields(Product product) throws DaoException {
		if (product.getCategoryId() != null) {
			product.setCategory(m_categoryDAO.get(product.getCategoryId()));
		}

		if (product.getUnitId() != null) {
			product.setUnit(m_unitDAO.get(product.getUnitId()));
		}

		//Add packages
		product.setPackages(m_packageDAO.findPackageByProduct(product.getId()));
	}



	@Override
	public List<Product> list() throws DaoException {
		ViewResult<?> result = m_db.queryView("product/all", Product.class, null, null);
		List<Product> rc = new ArrayList<Product>();
		
		for (ValueRow<?> row: result.getRows()) {
			Product product = get(row.getId());
			rc.add(product);
		}
		
		return rc;
	}
}
