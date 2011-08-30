package org.tagaprice.shared.entities.receiptManagement;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.svenson.JSONProperty;
import org.svenson.JSONTypeHint;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.shopmanagement.Shop;

/**
 * A single Receipt
 * Holds information about the creator {@link User} of the receipt,
 * the date when the receipt was created, a reference to the shop {@link Shop} where the receipt is from
 * and all {@link ReceiptEntry} of a receipt.
 * 
 */
public class Receipt extends Document {
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
	 * @param creator Creator of the current document revision
	 * @param title the title of the receipt
	 * @param date date of the receipt
	 * @param subsidiary the subsidiary
	 */
	public Receipt(User creator, String title, Date date, Shop shop) {
		super(creator, title);
		setDate(date);
		setShop(shop);
	}


	/**
	 * <b>UPDATE and GET</b>
	 * Update or select Receipt. Used on Client and Server
	 * @param creator Creator of the current document revision
	 * @param revisionId the revision of the receipt. (it is not really necessary to save the revision)
	 * @param title the title of the receipt
	 * @param date the date and time when a receipt was created
	 * @param subsidiary {@link ISubsidiary} where the receipt is from
	 */
	public Receipt(User creator, String receiptId, String revision, String title, Date date, Shop shop) {
		super(creator, receiptId, revision, title);
		setDate(date);
		setShop(shop);
	}


	/**
	 * @param receiptEntry add one {@link ReceiptEntry} to the {@link Receipt}
	 */
	//@JSONTypeHint(ReceiptEntry.class)
	public void addReceiptEntries(ReceiptEntry rc) {
		_receiptEntries.add(rc);
	}
	
	public void addReceiptEntries(List<ReceiptEntry> rc) {
		_receiptEntries.clear();
		for(ReceiptEntry r:rc){
			addReceiptEntries(r);
		}
	}

	/**
	 * @return the date of the {@link Receipt}
	 */
	@JSONProperty(ignore = true)
	public Date getDate() {
		return _date;
	}

	public Long getTimeStamp(){
		return _date.getTime();
	}

	public void setTimeStamp(Long timestamp){
		_date=new Date(timestamp);
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
	@JSONProperty(ignore = true)
	public Shop getShop() {
		return _shop;
	}
	
	public String getShopId(){
		String rc = null;
		if(getShop() != null)
			rc = getShop().getId();
		return rc;
	}
	
	public void setShopId(String shopId){
		setShop(new Shop(null, shopId, null, null, null));
	}



	/**
	 * Set the {@link Receipt} date
	 * @param date receipt date
	 */
	@JSONProperty(ignore = true)
	public void setDate(Date date) {
		_date=date;
	}



	/**
	 * Set a list of {@link ReceiptEntry}. All included {@link ReceiptEntry} will be overwritten!
	 * @param receiptEntries the list of {@link ReceiptEntry}
	 */
	//@JSONTypeHint(ReceiptEntry.class)
	public void setReceiptEntries(List<ReceiptEntry> rc) {

		_receiptEntries.clear();
		for(ReceiptEntry r:rc){
			addReceiptEntries(r);
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
