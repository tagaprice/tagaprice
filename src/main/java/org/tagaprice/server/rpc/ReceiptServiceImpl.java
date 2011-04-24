package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.shared.entities.receiptManagement.*;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {
	MyLogger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);
	private static final long serialVersionUID = 1L;
	IReceiptDAO receiptDAO;

	public ReceiptServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		receiptDAO = daoFactory.getReceiptDAO();
	}

	@Override
	public Receipt saveReceipt(Receipt receipt) {
		logger.log("Receipt saved: "+receipt);
		Receipt rc=null;

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
	public Receipt getReceipt(String receiptId) {
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
	public List<Receipt> getReceipts() throws UserNotLoggedInException {
		return receiptDAO.list();
	}

}
