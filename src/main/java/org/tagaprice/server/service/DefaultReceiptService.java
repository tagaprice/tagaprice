package org.tagaprice.server.service;

import java.util.List;

import org.tagaprice.core.api.IReceiptService;
import org.tagaprice.core.api.ServerException;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.server.dao.interfaces.IReceiptDAO;

public class DefaultReceiptService implements IReceiptService {
	IReceiptDAO _receiptDao;
	SessionService _sessionFactory;


	@Override
	public Receipt save(Receipt receipt) throws ServerException {
		// TODO replace this by ArgumentUtility
		if(receipt == null)
			throw new IllegalArgumentException("receipt is null");
		return _receiptDao.save(receipt);
	}

	@Override
	public List<ReceiptEntry> getReceiptEntriesByProductIdAndRev(long productId, int productRevision) {
		return _receiptDao.getReceiptEntriesByProductIdAndRev(productId, productRevision);
	}


	public void setReceiptDAO(IReceiptDAO receiptDao) {
		_receiptDao = receiptDao;
	}

	public void setSessionFactory(SessionService sessionFactory) {
		_sessionFactory = sessionFactory;
	}
}
