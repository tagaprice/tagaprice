package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.shared.entities.accountmanagement.Session;
import org.tagaprice.shared.entities.receiptManagement.*;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {
	MyLogger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);
	private static final long serialVersionUID = 1L;
	IReceiptDao receiptDAO;

	public ReceiptServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		receiptDAO = daoFactory.getReceiptDao();
	}

	@Override
	public Receipt saveReceipt(Session session, Receipt receipt) throws DaoException {
		logger.log("Receipt saved: "+receipt);
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
		logger.log("Receipt getReceipt: "+receiptId);
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
