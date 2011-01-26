package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.util.Date;
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
	private Set<IReceiptEntry> _receiptEntries;


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

	public Receipt(Long id, Date date, Shop shop, User user, Set<IReceiptEntry> receiptEntries) {
		_date = date;
		_shop = shop;
		_user = user;
		_receiptEntries = receiptEntries;
	}


	@Override
	public Date getDate() {
		return _date;
	}

	@Override
	public void setDate(Date date) {
		_date = date;
	}

	@Override
	public Shop getShop() {
		return _shop;
	}

	@Override
	public void setShop(Shop shop) {
		_shop = shop;
	}
	public IRevisionId getShopId(){
		return getShop().getRevisionId();
	}

	public void setShopId(IRevisionId shopId){
		// TODO
	}

	@Override
	public User getUser() {
		return _user;
	}

	@Override
	public void setUser(User user) {
		_user = user;
	}

	@Override
	public Set<IReceiptEntry> getReceiptEntries() {
		return _receiptEntries;
	}

	@Override
	public void setReceiptEntries(Set<IReceiptEntry> receiptEntries) {
		_receiptEntries = receiptEntries;
	}

	@Override
	public String toString() {
		return "Receipt [_date=" + _date + ", _shop=" + _shop  + ", _user=" + _user
		+ ", _receiptEntries=" + _receiptEntries + "]";
	}


	@Override
	public IReceipt copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_date == null) ? 0 : _date.hashCode());
		result = prime * result + ((_receiptEntries == null) ? 0 : _receiptEntries.hashCode());
		result = prime * result + ((_shop == null) ? 0 : _shop.hashCode());
		result = prime * result + ((_user == null) ? 0 : _user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receipt other = (Receipt) obj;
		if (_date == null) {
			if (other._date != null)
				return false;
		} else if (!_date.equals(other._date))
			return false;
		if (_receiptEntries == null) {
			if (other._receiptEntries != null)
				return false;
		} else if (!_receiptEntries.equals(other._receiptEntries))
			return false;
		if (_shop == null) {
			if (other._shop != null)
				return false;
		} else if (!_shop.equals(other._shop))
			return false;
		if (_user == null) {
			if (other._user != null)
				return false;
		} else if (!_user.equals(other._user))
			return false;
		return true;
	}





}
