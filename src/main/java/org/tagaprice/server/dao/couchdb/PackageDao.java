package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jcouchdb.db.Options;
import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IPackageDao;
import org.tagaprice.server.dao.IUnitDao;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class PackageDao extends DaoClass<Package> implements IPackageDao {

	IUnitDao m_unitDAO;

	public PackageDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Package.class, Document.Type.PACKAGE, null);
		m_unitDAO = daoFactory.getUnitDao();
	}

	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, List<Package>> listByProducts(String ... productIDs) throws DaoException {
		ViewResult<?> result = m_db.queryViewByKeys("package/byProduct", String.class, Arrays.asList(productIDs), new Options().includeDocs(true), null);
		Map<String, List<Package>> rc = new HashMap<String, List<Package>>();
		Set<String> unitIDs = new TreeSet<String>();

		for (ValueRow<?> row: result.getRows()) {
			Package pkg = docFromValueRow(row);
			if (!rc.containsKey(pkg.getProductId())) {
				rc.put(pkg.getProductId(), new ArrayList<Package>());
			}
			rc.get(pkg.getProductId()).add(pkg);
			
			if (pkg.getQuantity().getUnitId() != null) {
				unitIDs.add(pkg.getQuantity().getUnitId());
			}
		}

		Map<String, Unit> units = m_unitDAO.getBulkOnly(arrayFrom(String.class, unitIDs));
		
		for (Collection<Package> pkgs: rc.values()) {
			for (Package pkg: pkgs) {
				if (pkg.getQuantity().getUnitId() != null) {
					pkg.getQuantity().setUnit(units.get(pkg.getQuantity().getUnitId()));
				}
			}
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
	protected void _injectFields(Package ... packages) throws DaoException {
		Set<String> unitIDs = new TreeSet<String>();

		for (Package pkg: packages) {
			if (pkg.getQuantity() != null) {
				String unitId = pkg.getQuantity().getUnitId();
				if (unitId != null) unitIDs.add(unitId);
			}
		}

		Map<String, Unit> units = m_unitDAO.getBulk(unitIDs.toArray(new String[unitIDs.size()]));
		
		for (Package pkg: packages) {
			if (pkg.getQuantity() != null) {
				String unitId = pkg.getQuantity().getUnitId();
				pkg.getQuantity().setUnit(units.get(unitId));
			}
		}
	}
}
