package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ReceiptDao extends DaoClass<Receipt> implements IReceiptDao {
	public ReceiptDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Receipt.class, "receipt", daoFactory._getEntityDao());
	}
	
	@Override
	public List<Receipt> list() throws DaoException {
		ViewResult<?> result = m_db.queryView("receipt/all", Receipt.class, null, null);
		List<Receipt> rc = new ArrayList<Receipt>();
		
		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			Receipt receipt = get(row.getId());
			rc.add(receipt);
		}
		
		return rc;
	}

}
