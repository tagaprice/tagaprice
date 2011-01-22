package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.*;

@Entity
@SuppressWarnings("unused")
public class Receipt implements Serializable {

	private static final long serialVersionUID = 1L;

	private long _id;
	private BasicShop _shop;
	private Date _createdAt;
	private Account _creator;
	private Set<ReceiptEntry> _receiptEntries;

	protected Receipt() {
	}


	public Receipt(long id, BasicShop basicShop, Date createdAt, Account creator, Set<ReceiptEntry> receiptEntries) {
		_id = id;
		_shop = basicShop;
		_createdAt = createdAt;
		_creator = creator;
		_receiptEntries = receiptEntries;
	}


	@Id
	@Column(name = "receipt_id")
	public long getId() {
		return _id;
	}

	private void setId(long id) {
		_id = id;
	}


	@Column(name = "shop_id")
	public long getShopId() {
		return _shop.getShopId();
	}

	private void setShopId(long shopId) {
		// when setting, hibernate should set up a reference to BasicShop instead
		return; // TODO check if this works as expected
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shop_id", insertable = false, updatable = false)
	public BasicShop getShop() {
		return _shop;
	}

	private void setShop(BasicShop shop) {
		_shop = shop;
	}


	@Column(name = "created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}

	private void setCreatedAt(Date createdAt) {
		_createdAt = createdAt;
	}


	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "creator")
	public Account getCreator() {
		return _creator;
	}

	private void setCreator(Account creator) {
		_creator = creator;
	}


	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "receipt_id")
	public Set<ReceiptEntry> getReceiptEntries() {
		return _receiptEntries;
	}

	private void setReceiptEntries(Set<ReceiptEntry> receiptEntries) {
		_receiptEntries = receiptEntries;
	}



	@Override
	public String toString() {
		return "Receipt [_id=" + _id + ", _shop=" + _shop + ", _createdAt=" + _createdAt + ", _creator=" + _creator
		+ ", _receiptEntries=" + _receiptEntries + "]";
	}

	/**
	 * check this method before simple regeneration, only shopId of shop is used (since other properties are not
	 * significant for a {@link Receipt}).
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + ((_creator == null) ? 0 : _creator.hashCode());
		result = prime * result + (int) (_id ^ (_id >>> 32));
		result = prime * result + ((_receiptEntries == null) ? 0 : _receiptEntries.hashCode());
		result = prime * result
		+ ((_shop == null) ? 0 : (_shop.getShopId() == null) ? 0 : _shop.getShopId().hashCode());
		return result;
	}

	/**
	 * check this method before simple regeneration, shop is only compared by the id (since other properties are not
	 * significant for a {@link Receipt}).
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Receipt other = (Receipt) obj;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_creator == null) {
			if (other._creator != null)
				return false;
		} else if (!_creator.equals(other._creator))
			return false;
		if (_id != other._id)
			return false;
		if (_receiptEntries == null) {
			if (other._receiptEntries != null)
				return false;
		} else if (!_receiptEntries.equals(other._receiptEntries))
			return false;
		if (_shop == null) {
			if (other._shop != null)
				return false;
			// changed implementation here!
		} else if (_shop.getShopId() == null) {
			if (other._shop.getShopId() != null)
				return false;
		} else if (!_shop.getShopId().equals(other._shop.getShopId()))
			return false;
		return true;
	}
}
