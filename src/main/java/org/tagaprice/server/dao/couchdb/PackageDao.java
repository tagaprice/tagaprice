package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class PackageDao extends DaoClass<Package> implements IPackageDao {

	IUnitDao m_unitDAO;

	public PackageDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Package.class, "package", daoFactory._getDocumentDao());
		m_unitDAO = daoFactory.getUnitDao();
	}

	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Package> listByProduct(String productId) throws DaoException{
		List<Package> rc = new ArrayList<Package>();

		for(String packageId: listIDsByProduct(productId)) {
			rc.add(get(packageId));
		}

		return rc;
	}
	
	@Override
	public List<String> listIDsByProduct(String productId) throws DaoException {
		ViewResult<?> result = m_db.queryView("package/byProduct", String.class, new Options().key(productId), null);
		List<String> rc = new ArrayList<String>();


		for (ValueRow<?> row: result.getRows()) {
			String packageId = row.getId();
			rc.add(packageId);
		}

		return rc;
	}

	@Override
	protected void _injectFields(Package document) throws DaoException {

		if(document.getQuantity().getUnitId()!=null){
			document.getQuantity().setUnit(m_unitDAO.get(document.getQuantity().getUnitId()));
		}
	}
}
