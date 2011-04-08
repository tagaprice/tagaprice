package org.tagaprice.shared.rpc.receiptmanagement;

import java.util.List;

import org.tagaprice.shared.entities.receiptManagement.*;
import org.tagaprice.shared.exceptions.UserNotLoggedInException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("receiptservice")
public interface IReceiptService extends RemoteService {

	Receipt saveReceipt(Receipt receipt)  throws UserNotLoggedInException;

	Receipt getReceipt(String receiptid) throws UserNotLoggedInException;

	List<Receipt> getReceipts() throws UserNotLoggedInException;


	/**
	 * TODO This method must include, boundingBox, and productId
	 * @param productid
	 * @return
	 */
	@Deprecated
	List<ReceiptEntry> getReceiptEntriesByProductId(long productid);

}
