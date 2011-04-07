package org.tagaprice.shared.rpc.receiptmanagement;

import java.util.List;

import org.tagaprice.shared.entities.receiptManagement.*;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IReceiptServiceAsync {

	void getReceipt(String receiptId, AsyncCallback<IReceipt> callback);

	void  getReceits( AsyncCallback<List<IReceipt>> callback);

	@Deprecated
	void getReceiptEntriesByProductId(long productid, AsyncCallback<List<ReceiptEntry>> callback);

	void saveReceipt(IReceipt receipt, AsyncCallback<IReceipt> callback);

}
