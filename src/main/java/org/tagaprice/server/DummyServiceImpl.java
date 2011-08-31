package org.tagaprice.server;

import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.rpc.DummyService;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class DummyServiceImpl extends RemoteServiceServlet implements
	DummyService {

	@Override
	public void dummyMethode(Boolean bool, boolean bool2, ReceiptEntry eceiptEntry) {
		// TODO Auto-generated method stub
		
	}



  


}
