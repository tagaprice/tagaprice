package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.*;

import org.tagaprice.server.dao.interfaces.IReceiptDAO;

@Entity
@SuppressWarnings("unused")
public class Shop implements Serializable {

	private static final long serialVersionUID = 1L;

	private long _id;
	private String _title;
	private Set<Receipt> _receipts;


	protected Shop() {
	}

	public Shop(long id, String title, Set<Receipt> receipts) {
		_id = id;
		_title = title;
		_receipts = receipts;
	}


	@Id
	@Column(name = "shop_id")
	public long getId() {
		return _id;
	}

	private void setId(long id) {
		_id = id;
	}


	public String getTitle() {
		return _title;
	}

	private void setTitle(String title) {
		_title = title;
	}


	/**
	 * This Receipts won't get saved or updated. Save them using {@link IReceiptDAO}.
	 */
	@OneToMany(fetch = FetchType.LAZY) // TODO check error why when set to eager
	@JoinColumn(name = "receipt_id", updatable = false, insertable = false)
	public Set<Receipt> getReceipts() {
		return _receipts;
	}

	private void setReceipts(Set<Receipt> receipts) {
		_receipts = receipts;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (_id ^ (_id >>> 32));
		result = prime * result + ((_title == null) ? 0 : _title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Shop other = (Shop) obj;
		if (_id != other._id)
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Shop [_id=" + _id + ", _title=" + _title + "]";
	}

}