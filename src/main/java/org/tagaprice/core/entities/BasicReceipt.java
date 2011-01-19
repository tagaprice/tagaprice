package org.tagaprice.core.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * <p>
 * Don't attempt to save this entity! Use {@link Receipt} instead!
 * </p>
 * <p>
 * This class only provides some basic information about a receipt, i.e. it doesn't provide any {@link ReceiptEntry}s.
 * </p>
 * 
 * @author haja
 * 
 */
@Entity
@Table(name = "receipt")
@SuppressWarnings("unused")
public class BasicReceipt implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long _receiptId;
	private BasicShop _basicShop;

	protected BasicReceipt() {
	}

	public BasicReceipt(long receiptId, BasicShop basicShop) {
		_receiptId = receiptId;
		_basicShop = basicShop;
	}


	@Id
	@Column(name = "receipt_id")
	public Long getId() {
		return _receiptId;
	}

	private void setId(Long receiptId) {
		_receiptId = receiptId;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shop_id")
	public BasicShop getBasicShop() {
		return _basicShop;
	}

	private void setBasicShop(BasicShop basicShop) {
		_basicShop = basicShop;
	}



	@Override
	public String toString() {
		return "BasicReceipt [_receiptId=" + _receiptId + ", _basicShop=" + _basicShop + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_basicShop == null) ? 0 : _basicShop.hashCode());
		result = prime * result + ((_receiptId == null) ? 0 : _receiptId.hashCode());
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
		BasicReceipt other = (BasicReceipt) obj;
		if (_basicShop == null) {
			if (other._basicShop != null)
				return false;
		} else if (!_basicShop.equals(other._basicShop))
			return false;
		if (_receiptId == null) {
			if (other._receiptId != null)
				return false;
		} else if (!_receiptId.equals(other._receiptId))
			return false;
		return true;
	}
}
