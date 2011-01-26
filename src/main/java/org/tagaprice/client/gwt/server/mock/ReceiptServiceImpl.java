package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.client.gwt.shared.rpc.receiptManagement.IReceiptService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3420788026998858664L;

	@Override
	public IReceipt save(IReceipt receipt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IReceipt getReceipt(long receiptid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<ReceiptEntry> getReceiptEntriesByProductId(long productid) {
		// TODO Auto-generated method stub
		return null;
	}

}
