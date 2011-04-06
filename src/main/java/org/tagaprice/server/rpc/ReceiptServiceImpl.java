package org.tagaprice.server.rpc;

import java.util.List;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.receiptManagement.*;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;
import org.tagaprice.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {
	MyLogger logger = LoggerFactory.getLogger(ReceiptServiceImpl.class);
	private static final long serialVersionUID = 3420788026998858664L;
	IReceiptDAO receiptDAO;

	public ReceiptServiceImpl(IDaoFactory daoFactory) {
		receiptDAO = daoFactory.getReceiptDAO();
	}

	@Override
	public IReceipt saveReceipt(IReceipt receipt) {
		logger.log("Receipt saved: "+receipt);
		IReceipt rc=null;

		//Create or update Receipt
		if (receipt.getRevisionId()==null){
			rc = receiptDAO.create(receipt);
		}
		else {
			rc = receiptDAO.update(receipt);
		}

		return rc;
	}

	@Override
	public IReceipt getReceipt(long receiptId) {
		return receiptDAO.get(new RevisionId(receiptId));
	}

	@Override
	@Deprecated
	public List<ReceiptEntry> getReceiptEntriesByProductId(long productid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IReceipt> getReceits() throws UserNotLoggedInException {
		return receiptDAO.list();
	}

}
