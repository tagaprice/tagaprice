package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class PackageDao extends DaoClass<Package> implements IPackageDao {

	IUnitDao m_unitDAO;

	public PackageDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Package.class, "package", daoFactory._getEntityDao());
		m_unitDAO = daoFactory.getUnitDao();
	}

	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Package> findPackageByProduct(String productId) throws DaoException{

		ViewResult<?> result = m_db.queryViewByKeys("package/toproduct", Package.class, Arrays.asList(productId), null, null);
		List<Package> rc = new ArrayList<Package>();

		for (ValueRow<?> row: result.getRows()) {
			Package packa = get(row.getId());

			rc.add(packa);
		}

		return rc;
	}

	@Override
	protected void _injectFields(Package entity) throws DaoException {

		if(entity.getQuantity().getUnitId()!=null){
			entity.getQuantity().setUnit(m_unitDAO.get(entity.getQuantity().getUnitId()));
		}
	}

}
