package org.tagaprice.client.gwt.server.diplomat.converter;

import java.util.HashSet;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;

public class ReceiptConverter {

	private static final ReceiptConverter converter = new ReceiptConverter();

	public static ReceiptConverter getInstance() {
		return ReceiptConverter.converter;
	}

	public IReceipt convertCoreReceiptToGWTReceipt(Receipt coreReceipt) {
		return null;
	}

	public Receipt convertGWTReceiptToCoreReceipt(IReceipt gwtReceipt) {
		ReceiptEntryConverter receiptEntryConverter = ReceiptEntryConverter.getInstance();
		Receipt coreReceipt = null;
		if(gwtReceipt.getRevisionId() != null && gwtReceipt.getRevisionId().getId() != 0) {
			//this is a new product
			Long receiptId = null;
			BasicShop basicShop = new BasicShop(gwtReceipt.getShop().getRevisionId().getId(), gwtReceipt.getShop().getTitle());

			HashSet<ReceiptEntry> receiptEntries = new HashSet<ReceiptEntry>();
			for(IReceiptEntry gwtRE: gwtReceipt.getReceiptEntries()) {
				receiptEntries.add(receiptEntryConverter.convertGWTReceiptEntryToCoreReceiptEntry(gwtRE));
			}
			coreReceipt = new Receipt(receiptId, basicShop, DefaultValues.defaultDate, DefaultValues.defaultCoreAccount, receiptEntries);


		} else {
			//this is an existing receipt

		}

		return coreReceipt;
	}

	public IReceipt convertBasicReceiptToGWTReceipt(BasicReceipt coreBasicReceipt) {
		IReceipt receipt = new org.tagaprice.client.gwt.shared.entities.receiptManagement.Receipt();





		return receipt;
	}

}
