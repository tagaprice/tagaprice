package org.tagaprice.client.gwt.server.diplomat.converter;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceipt;
import org.tagaprice.core.entities.Receipt;

public class ReceiptConverter {

	private static final ReceiptConverter converter = new ReceiptConverter();

	public static ReceiptConverter getInstance() {
		return ReceiptConverter.converter;
	}

	public IReceipt convertCoreReceiptToGWTReceipt(Receipt coreReceipt) {
		return null;
	}

	public Receipt convertGWTReceiptToCoreReceipt(IReceipt gwtReceipt) {
		return null;
	}

}
