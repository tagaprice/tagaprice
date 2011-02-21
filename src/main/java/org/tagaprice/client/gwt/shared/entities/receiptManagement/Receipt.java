package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import java.util.Date;
import java.util.Set;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.User;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

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
	private IAddress _address;
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

	public Receipt(Long id, Date date, IAddress address, User user, Set<IReceiptEntry> receiptEntries) {
		setDate(date);
		setAddress(address);
		setUser(user);
		setReceiptEntries(receiptEntries);
	}


	@Override
	public Date getDate() {
		return _date;
	}

	@Override
	public void setDate(Date date) {
		_date = date;
	}


	public IRevisionId getShopId(){
		return getAddress().getRevisionID();
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
		_receiptEntries.clear();
		_receiptEntries.addAll(receiptEntries);
	}

	@Override
	public String toString() {
		return "Receipt [_date=" + _date + ", _address=" + _address  + ", _user=" + _user
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
		result = prime * result + ((_address == null) ? 0 : _address.hashCode());
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
		if (_address == null) {
			if (other._address != null)
				return false;
		} else if (!_address.equals(other._address))
			return false;
		if (_user == null) {
			if (other._user != null)
				return false;
		} else if (!_user.equals(other._user))
			return false;
		return true;
	}

	@Override
	public IAddress getAddress() {
		return _address;
	}

	@Override
	public void setAddress(IAddress address) {
		_address=address;
	}





}
