package org.tagaprice.core.entities;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

/**
 * @author haja
 *
 */
@Entity
@SuppressWarnings("unused")
public class ReceiptEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long _receiptId;
	private Long _productId;
	//	private ProductRevision _productRevision;
	private int _count;
	private long _price;
	private Integer _productRevNumber;
	private BasicReceipt _basicReceipt;


	protected ReceiptEntry() {
	}

	public ReceiptEntry(long receiptId, long productId, int productRevisionNumber, int count, long price, BasicReceipt basicReceipt) {
		_receiptId = receiptId;
		_productId = productId;
		_productRevNumber = productRevisionNumber;
		_count = count;
		_price = price;
		_basicReceipt = basicReceipt;
	}

	@Id
	@Column(name = "receipt_id")
	public Long getReceiptId() {
		return _receiptId;
	}

	private void setReceiptId(Long receiptId) {
		_receiptId = receiptId;
	}


	@Id
	@Column(name = "product_id")
	public Long getProductId() {
		return _productId;
	}

	private void setProductId(Long productId) {
		_productId = productId;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "receipt_id", insertable = false, updatable = false)
	public BasicReceipt getBasicReceipt() {
		return _basicReceipt;
	}
	private void setBasicReceipt(BasicReceipt basicReceipt) {
		_basicReceipt = basicReceipt;
	}



	@Column(name = "product_revision")
	public Integer getProductRevisionNumber() {
		return _productRevNumber;
	}

	private void setProductRevisionNumber(int productRevision) {
		_productRevNumber = productRevision;
	}


	// @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	// @JoinColumns({ @JoinColumn(name = "product_id"), @JoinColumn(name = "product_revision") })
	//	@Transient
	//	public ProductRevision getProductRevision() {
	//		return _productRevision;
	//	}
	//
	//	private void setProductRevision(ProductRevision productRevision) {
	//		_productRevision = productRevision;
	//	}

	@Column(name = "product_count")
	public int getCount() {
		return _count;
	}

	private void setCount(int count) {
		_count = count;
	}


	public long getPrice() {
		return _price;
	}

	private void setPrice(long price) {
		_price = price;
	}


	@Override
	public String toString() {
		return "ReceiptEntry [_receiptId=" + _receiptId + ", _productId=" + _productId + ", _count=" + _count
		+ ", _price=" + _price + ", _productRevNumber=" + _productRevNumber + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _count;
		result = prime * result + (int) (_price ^ (_price >>> 32));
		result = prime * result + ((_productId == null) ? 0 : _productId.hashCode());
		result = prime * result + ((_productRevNumber == null) ? 0 : _productRevNumber.hashCode());
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
		ReceiptEntry other = (ReceiptEntry) obj;
		if (_count != other._count)
			return false;
		if (_price != other._price)
			return false;
		if (_productId == null) {
			if (other._productId != null)
				return false;
		} else if (!_productId.equals(other._productId))
			return false;
		if (_productRevNumber == null) {
			if (other._productRevNumber != null)
				return false;
		} else if (!_productRevNumber.equals(other._productRevNumber))
			return false;
		if (_receiptId == null) {
			if (other._receiptId != null)
				return false;
		} else if (!_receiptId.equals(other._receiptId))
			return false;
		return true;
	}
}
