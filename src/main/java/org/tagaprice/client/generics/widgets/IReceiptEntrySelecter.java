package org.tagaprice.client.generics.widgets;

import java.util.List;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import com.google.gwt.user.client.ui.IsWidget;

public interface IReceiptEntrySelecter extends IsWidget {

	/**
	 * Set some {@link ReceiptEntry} to the {@link IReceiptEntrySelecter}
	 * @param receiptEntries that will be added to the {@link IReceiptEntrySelecter}
	 */
	public void setReceiptEntries(List<ReceiptEntry> receiptEntries);

	/**
	 * Add one {@link ReceiptEntry}
	 * @param receiptEntry new entry
	 */
	public void addReceiptEntrie(ReceiptEntry receiptEntry);

	/**
	 * Return all {@link ReceiptEntry}
	 * @return all {@link ReceiptEntry}
	 */
	public List<ReceiptEntry> getReceiptEntries();
}
