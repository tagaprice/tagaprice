package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.client.gwt.shared.entities.IEntity;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.ISubsidiary;


/**
 * A {@link IReceipt} contains all informations after a user was in an {@link Subsidiary}
 * The {@link IReceipt} informations are used to combine a Subsidiary with a Package and a price
 */
public interface IReceipt extends IEntity<IReceipt> {


	/**
	 * @param receiptEntry add one {@link IReceiptEntry} to the {@link IReceipt}
	 */
	public void addReceiptEntriy(IReceiptEntry receiptEntry);


	/**
	 * @return all included {@link IReceiptEntry}
	 */
	public ArrayList<IReceiptEntry> getReceiptEntries();

	/**
	 * @return the subsidiary where the user shopped
	 */
	public ISubsidiary getSubsidiary();

	/**
	 * Set a list of {@link IReceiptEntry}. All included {@link IReceiptEntry} will be overwritten!
	 * @param receiptEntries the list of {@link IReceiptEntry}
	 */
	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries);

	/**
	 * Set the {@link ISubsidiary} where the user bought the things.
	 * @param subsidiary
	 */
	public void setSubsidiary(ISubsidiary subsidiary);

	/**
	 * Set the {@link IReceipt} date
	 * @param date receipt date
	 */
	public void setDate(Date date);

	/**
	 * @return the date of the {@link IReceipt}
	 */
	public Date getDate();


}