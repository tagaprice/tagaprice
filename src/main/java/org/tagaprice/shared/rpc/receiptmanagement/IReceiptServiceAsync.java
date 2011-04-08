package org.tagaprice.shared.rpc.receiptmanagement;

import java.util.List;

import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface IReceiptServiceAsync {

	void getReceipt(String receiptId, AsyncCallback<Receipt> callback);

	void getReceipts(AsyncCallback<List<Receipt>> callback);

	@Deprecated
	void getReceiptEntriesByProductId(long productid, AsyncCallback<List<ReceiptEntry>> callback);

	void saveReceipt(Receipt receipt, AsyncCallback<Receipt> callback);

}
