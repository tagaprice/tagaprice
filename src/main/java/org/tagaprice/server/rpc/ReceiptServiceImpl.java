package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.ISessionDao;
import org.tagaprice.shared.entities.accountmanagement.Session;
import org.tagaprice.shared.entities.receiptManagement.*;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {
	private static final long serialVersionUID = 1L;
	IReceiptDao receiptDAO;
	ISessionDao sessionDAO;

	public ReceiptServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		receiptDAO = daoFactory.getReceiptDao();
		sessionDAO = daoFactory.getSessionDao();
	}

	@Override
	public Receipt saveReceipt(String sessionId, Receipt receipt) throws DaoException {
		Log.debug("Receipt saved: "+receipt);

		if (sessionId == null) throw new DaoException("Can't save a receipt without having a valid session!");

		Session session = sessionDAO.get(sessionId);
		Receipt rc=null;

		// TODO check session validity
		receipt.setCreator(session.getCreator());

		//Create or update Receipt
		if (receipt.getId()==null){
			rc = receiptDAO.create(receipt);
		}
		else {
			rc = receiptDAO.update(receipt);
		}

		return rc;
	}

	@Override
	public Receipt getReceipt(String receiptId) throws DaoException {
		Log.debug("Receipt getReceipt: "+receiptId);
		return receiptDAO.get(receiptId);
	}

	@Override
	@Deprecated
	public List<ReceiptEntry> getReceiptEntriesByProductId(long productid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Receipt> getReceipts() throws UserNotLoggedInException, DaoException {
		return receiptDAO.list();
	}

}
