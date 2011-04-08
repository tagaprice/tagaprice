package org.tagaprice.server.dao.mock;

import java.util.HashMap;
import java.util.List;

import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class PackageDAO implements IPackageDAO {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	HashMap<IRevisionId, Package> packageAllRevisions = new HashMap<IRevisionId, Package>();
	int revIdCounter = 0;
	
	@Override
	public Package create(Package pkg) {
		pkg.setId(new Integer(this.revIdCounter++).toString());
		pkg.setRevision("1");
		logger.log("create package. ID="+this.revIdCounter);
		return pkg;
	}

	@Override
	public Package get(String id, String revision) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Package get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Package update(Package pkg) {
		pkg.setRevision(pkg.getRevision() + 1);

		packageAllRevisions.put(
				new RevisionId(pkg.getId(), pkg.getRevision()), pkg);
		return pkg;
	}

	@Override
	public void delete(Package pkg) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Package> find(Package searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
