package org.tagaprice.client.gwt.shared.rpc.receiptmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.core.api.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("receiptservice")
public interface IReceiptService extends RemoteService {

	IReceipt save(IReceipt receipt)  throws UserNotLoggedInException;

	IReceipt getReceipt(long receiptid) throws UserNotLoggedInException;

	ArrayList<ReceiptEntry> getReceiptEntriesByProductId(long productid);

}
