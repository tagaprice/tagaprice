package org.tagaprice.shared.rpc.receiptmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.receiptManagement.*;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IReceiptServiceAsync {

	void getReceipt(long receiptid, AsyncCallback<IReceipt> callback);

	void  getReceits( AsyncCallback<ArrayList<IReceipt>> callback);

	@Deprecated
	void getReceiptEntriesByProductId(long productid, AsyncCallback<ArrayList<ReceiptEntry>> callback);

	void saveReceipt(IReceipt receipt, AsyncCallback<IReceipt> callback);

}
