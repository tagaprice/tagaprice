package org.tagaprice.server.rpc;

import java.math.BigDecimal;
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

public class ReceiptServiceImpl extends ASessionService implements IReceiptService {
	private static final long serialVersionUID = 1L;
	IReceiptDao receiptDAO;

	public ReceiptServiceImpl() {
		IDaoFactory daoFactory = InitServlet.getDaoFactory();
		receiptDAO = daoFactory.getReceiptDao();
	}

	@Override
	public Receipt saveReceipt(String sessionId, Receipt receipt) throws DaoException, UserNotLoggedInException {
		Log.debug("Receipt saved: "+receipt);
		
		
		Receipt rc=null;

		// TODO check session validity
		receipt.setCreatorId(getUser().getCreatorId());

		//check if a package is new
		for(ReceiptEntry re: receipt.getReceiptEntries()){
			if(re.getPackageId()==null){
				if(!re.getPackage().getQuantity().getQuantity().equals(new BigDecimal("0.0")) 
						&& !re.getPackage().getQuantity().getQuantity().equals(new BigDecimal("0"))){
					re.getPackage().setCreatorId(getUser().getCreatorId());
				}else
					throw new DaoException("package size must not be 0");
			}
		}

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
	public List<Receipt> getReceipts(String sessionId) throws UserNotLoggedInException, DaoException {
		
		return receiptDAO.listByUser(getUser().getId());
	}

}
