package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.receiptManagement.IReceiptEntry;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is only a wrapper class
 *
 */
public class ReceiptEntrySelecter extends Composite implements IReceiptEntrySelecter {

	private IReceiptEntrySelecter receiptEntrySelecter = GWT.create(IReceiptEntrySelecter.class);

	public ReceiptEntrySelecter() {
		initWidget(receiptEntrySelecter.asWidget());
	}

	@Override
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries) {
		receiptEntrySelecter.setReceiptEntries(receiptEntries);
	}

	@Override
	public void addReceiptEntrie(IReceiptEntry receiptEntry) {
		receiptEntrySelecter.addReceiptEntrie(receiptEntry);
	}

	@Override
	public ArrayList<IReceiptEntry> getReceiptEntries() {
		return receiptEntrySelecter.getReceiptEntries();
	}

}
