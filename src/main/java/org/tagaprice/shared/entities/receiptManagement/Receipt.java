package org.tagaprice.shared.entities.receiptManagement;

import java.util.ArrayList;
import java.util.Date;
import org.tagaprice.shared.entities.AEntity;
import org.tagaprice.shared.entities.shopmanagement.Shop;

/**
 * A single Receipt
 * Holds information about the creator {@link User} of the receipt,
 * the date when the receipt was created, a reference to the shop {@link Shop} where the receipt is from
 * and all {@link ReceiptEntry} of a receipt.
 * 
 */
public class Receipt extends AEntity {
	private static final long serialVersionUID = 1L;

	private Shop _shop;
	private Date _date;
	private ArrayList<ReceiptEntry> _receiptEntries = new ArrayList<ReceiptEntry>();

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Receipt() {
		super();
	}

	/**
	 * <b>NEW</b>
	 * Create new Receipt. Used on the Client
	 * @param title the title of the receipt
	 * @param date date of the receipt
	 * @param subsidiary the subsidiary
	 */
	public Receipt(String title, Date date, Shop shop) {
		super(title);
		setDate(date);
		setShop(shop);
	}


	/**
	 * <b>UPDATE and GET</b>
	 * Update or select Receipt. Used on Client and Server
	 * @param revisionId the revision of the receipt. (it is not really necessary to save the revision)
	 * @param title the title of the receipt
	 * @param date the date and time when a receipt was created
	 * @param subsidiary {@link ISubsidiary} where the receipt is from
	 */

	public Receipt(String receiptId, String revision, String title, Date date, Shop shop) {
		super(receiptId, revision, title);
		setDate(date);
		setShop(shop);
	}


	/**
	 * @param receiptEntry add one {@link ReceiptEntry} to the {@link Receipt}
	 */
	public void addReceiptEntriy(ReceiptEntry receiptEntry) {
		receiptEntry.setReceipt(this);
		_receiptEntries.add(receiptEntry);

	}

	/**
	 * @return the date of the {@link Receipt}
	 */
	public Date getDate() {
		return _date;
	}

	/**
	 * @return all included {@link ReceiptEntry}
	 */
	public ArrayList<ReceiptEntry> getReceiptEntries() {
		return _receiptEntries;
	}

	/**
	 * @return the subsidiary where the user shopped
	 */
	public Shop getShop() {
		return _shop;
	}

	/**
	 * @return the current shopId or null
	 */
	public String getShopId(){
		return _shop.getId();
	}

	/**
	 * Set the {@link Receipt} date
	 * @param date receipt date
	 */
	public void setDate(Date date) {
		_date=date;
	}

	/**
	 * Set a list of {@link ReceiptEntry}. All included {@link ReceiptEntry} will be overwritten!
	 * @param receiptEntries the list of {@link ReceiptEntry}
	 */
	public void setReceiptEntries(ArrayList<ReceiptEntry> receiptEntries) {
		_receiptEntries.clear();
		for(ReceiptEntry r:receiptEntries){
			addReceiptEntriy(r);
		}
	}

	/**
	 * Set the {@link shop} where the user bought the things.
	 * @param subsidiary
	 */
	public void setShop(Shop shop) {
		_shop=shop;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString() + "Receipt [_shop=" + _shop.getTitle() + ", _date=" + _date + "]";
	}






}
