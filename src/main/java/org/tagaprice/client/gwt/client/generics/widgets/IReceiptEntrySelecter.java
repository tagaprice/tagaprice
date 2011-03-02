package org.tagaprice.client.gwt.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;

import com.google.gwt.user.client.ui.IsWidget;

public interface IReceiptEntrySelecter extends IsWidget {

	/**
	 * Set some {@link IReceiptEntry} to the {@link IReceiptEntrySelecter}
	 * @param receiptEntries that will be added to the {@link IReceiptEntrySelecter}
	 */
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries);

	/**
	 * Add one {@link IReceiptEntry}
	 * @param receiptEntry new entry
	 */
	public void addReceiptEntrie(IReceiptEntry receiptEntry);

	/**
	 * Return all {@link IReceiptEntry}
	 * @return all {@link IReceiptEntry}
	 */
	public ArrayList<IReceiptEntry> getReceiptEntries();
}
