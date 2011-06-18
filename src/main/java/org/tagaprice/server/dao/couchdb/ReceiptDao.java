package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;

public class ReceiptDao extends DaoClass<Receipt> implements IReceiptDao {
	public ReceiptDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Receipt.class, "receipt", daoFactory._getEntityDao());
	}

	@Override
	public List<Receipt> list() throws DaoException {
		Log.debug("List");
		ViewResult<?> result = m_db.queryView("receipt/all", Receipt.class, null, null);
		List<Receipt> rc = new ArrayList<Receipt>();

		Log.debug("Listsize: "+result.getRows().size());
		for (ValueRow<?> row: result.getRows()) {
			Log.debug("rowId: "+row.getId());
			Receipt receipt = get(row.getId());
			rc.add(receipt);
		}

		return rc;
	}

}
