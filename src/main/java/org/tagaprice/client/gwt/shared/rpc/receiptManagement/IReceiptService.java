package org.tagaprice.client.gwt.shared.rpc.receiptManagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("receiptservice")
public interface IReceiptService extends RemoteService {

	IReceipt save(IReceipt receipt);

	IReceipt getReceipt(long receiptid);

	ArrayList<ReceiptEntry> getReceiptEntriesByProductId(long productid);

}
