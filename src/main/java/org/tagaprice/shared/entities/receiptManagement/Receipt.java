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


	public void addReceiptEntriy(ReceiptEntry receiptEntry) {
		receiptEntry.setReceipt(this);
		_receiptEntries.add(receiptEntry);

	}

	public Shop getShop() {
		return _shop;
	}

	public ArrayList<ReceiptEntry> getReceiptEntries() {
		return _receiptEntries;
	}

	public String getShopId(){
		return _shop.getId();
	}

	public void setShop(Shop shop) {
		_shop=shop;
	}

	public void setReceiptEntries(ArrayList<ReceiptEntry> receiptEntries) {
		_receiptEntries.clear();
		_receiptEntries.addAll(receiptEntries);
	}

	public void setDate(Date date) {
		_date=date;
	}

	public Date getDate() {
		return _date;
	}
}
