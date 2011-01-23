package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.sql.Date;
import java.util.Set;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.User;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop;
import org.tagaprice.core.entities.ReceiptEntry;

/**
 * A single Receipt
 * Holds information about the creator {@link User} of the receipt,
 * the date when the receipt was created, a reference to the shop {@link Shop} where the receipt is from
 * and all {@link ReceiptEntry} of a receipt.
 * 
 * @author Helga Weik (kaltra)
 *
 */
public class Receipt extends AEntity<IReceipt> implements IReceipt {



	private static final long serialVersionUID = -1411130663050015079L;

	private Date _date;
	private Shop _shop;
	private User _user;
	private Set<ReceiptEntry> _receiptEntries;


	public Receipt() {
		super();

	}

	/**
	 * Initializes a new {@link Receipt}
	 * @param id
	 * @param date the date and time when a receipt was created
	 * @param shop {@link Shop} where the receipt is from
	 * @param user {@link User} who created the receipt
	 * @param receiptEntries  {@link ReceiptEntry}
	 */

	public Receipt(Long id, Date date, Shop shop, User user, Set<ReceiptEntry> receiptEntries) {
		super();
		_date = date;
		_shop = shop;
		_user = user;
		_receiptEntries = receiptEntries;
	}


	public Date getDate() {
		return _date;
	}

	public void setDate(Date date) {
		_date = date;
	}

	public Shop getShop() {
		return _shop;
	}

	public void setShop(Shop shop) {
		_shop = shop;
	}
	public IRevisionId getShopId(){
		return getShop().getRevisionId();
	}

	public void setShopId(IRevisionId shopId){
		// TODO
	}

	public User getUser() {
		return _user;
	}

	public void setUser(User user) {
		_user = user;
	}

	public Set<ReceiptEntry> getReceiptEntries() {
		return _receiptEntries;
	}

	public void setReceiptEntries(Set<ReceiptEntry> receiptEntries) {
		_receiptEntries = receiptEntries;
	}

	@Override
	public String toString() {
		return "Receipt [_date=" + _date + ", _shop=" + _shop  + ", _user=" + _user
		+ ", _receiptEntries=" + _receiptEntries + "]";
	}

	//TODO
	public boolean equals(){
		return false;

	}

	@Override
	public IReceipt copy() {
		// TODO Auto-generated method stub
		return null;
	}

	//TODO
	public int HashCode(){
		return 0;
	}


}
