package org.tagaprice.client.gwt.server.diplomat;

import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.client.gwt.shared.rpc.receiptmanagement.IReceiptService;
import org.tagaprice.server.boot.Boot;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ReceiptServiceImpl extends RemoteServiceServlet implements IReceiptService {
	Logger _log = LoggerFactory.getLogger(ReceiptServiceImpl.class);
	org.tagaprice.core.api.IReceiptService _coreReceiptService;

	/**
	 * 
	 */
	private static final long serialVersionUID = 3420788026998858664L;

	public ReceiptServiceImpl() {
		String service = "defaultReceiptService";
		_log.debug("Attempting to load "+service+" from core.api");
		_coreReceiptService = (org.tagaprice.core.api.IReceiptService) Boot.getApplicationContext().getBean(service);
		_log.debug("Loaded "+service+" successfully.");
	}


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
