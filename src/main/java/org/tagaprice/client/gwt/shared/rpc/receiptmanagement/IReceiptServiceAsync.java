package org.tagaprice.client.gwt.shared.rpc.receiptmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IReceiptServiceAsync {

	void getReceipt(long receiptid, AsyncCallback<IReceipt> callback);

	void getReceiptEntriesByProductId(long productid, AsyncCallback<ArrayList<ReceiptEntry>> callback);

	void save(IReceipt receipt, AsyncCallback<IReceipt> callback);

}
