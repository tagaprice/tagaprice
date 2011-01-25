package org.tagaprice.client.gwt.server.diplomat.converter;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.core.entities.*;

public class ReceiptEntryConverter {

	private static final ReceiptEntryConverter instance = new ReceiptEntryConverter();

	public ReceiptEntryConverter getInstance() {
		return ReceiptEntryConverter.instance;
	}

	public IReceiptEntry convertCoreReceiptEntryToGWTReceiptEntry(ReceiptEntry coreReceiptEntry) {
		IReceiptEntry gwtReceiptEntry = new org.tagaprice.client.gwt.shared.entities.receiptManagement.ReceiptEntry();



		return gwtReceiptEntry;
	}

	/**
	 * This Methode should only be called from ReceiptConverter so it will always be part of an full Receipt.
	 * @param gwtReceiptEntry
	 * @return
	 */
	public ReceiptEntry convertGWTReceiptEntryToCoreReceiptEntry(IReceiptEntry gwtReceiptEntry) {
		ReceiptEntry coreReceiptEntry;
		BasicReceipt basicReceipt = new BasicReceipt(0L, null, null);

		ProductRevision productRevision = null;
		if(gwtReceiptEntry.getRevisionId() != null) {
			productRevision = new ProductRevision(gwtReceiptEntry.getRevisionId().getId(), gwtReceiptEntry.getRevisionId().getRevision(), "", null, null, null, 0., null, "");
		}
		int count = gwtReceiptEntry.getQuantity();
		int pricePerItem = gwtReceiptEntry.getPrice();

		coreReceiptEntry = new ReceiptEntry(basicReceipt, productRevision, count, pricePerItem);

		return coreReceiptEntry;
	}

}
