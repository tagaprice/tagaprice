package org.tagaprice.server.dao.mock;

import java.util.HashMap;
import java.util.List;

import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.productmanagement.IPackage;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class PackageDAO implements IPackageDAO {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	HashMap<IRevisionId, IPackage> packageAllRevisions = new HashMap<IRevisionId, IPackage>();
	int revIdCounter = 0;
	
	@Override
	public IPackage create(IPackage pkg) {
		pkg.setId(new Integer(this.revIdCounter++).toString());
		pkg.setRevision("1");
		logger.log("create package. ID="+this.revIdCounter);
		return pkg;
	}

	@Override
	public IPackage get(String id, String revision) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public IPackage get(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IPackage update(IPackage pkg) {
		pkg.setRevision(pkg.getRevision() + 1);

		packageAllRevisions.put(
				new RevisionId(pkg.getId(), pkg.getRevision()), pkg);
		return pkg;
	}

	@Override
	public void delete(IPackage pkg) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IPackage> find(IPackage searchPattern) {
		// TODO Auto-generated method stub
		return null;
	}

}
