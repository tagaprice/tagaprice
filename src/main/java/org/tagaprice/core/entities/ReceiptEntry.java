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

import org.tagaprice.server.helper.ArgumentUtitlity;

/**
 * <p>
 * This represents a single entry of a {@link Receipt}.
 * </p>
 * <p>
 * For every product on a receipt, a receipt entry has to be created. It holds information about the count of a specific
 * product and the price for one item of such a product.
 * </p>
 * <p>
 * This class is immutable. Properties once set, cannot be changed.
 * </p>
 * 
 * @see Receipt
 * @see ProductRevision
 * 
 * @author haja
 * 
 */
@Entity
@SuppressWarnings("unused")
public class ReceiptEntry implements Serializable {

	private static final long serialVersionUID = 1L;

	private BasicReceipt _basicReceipt;
	private ProductRevision _productRevision;
	private int _count;
	private long _pricePerItem;

	private Long _receiptId;
	private Long _productId;



	protected ReceiptEntry() {
	}

	/**
	 * Initializes a new {@link ReceiptEntry}.
	 * 
	 * @param basicReceipt
	 *            Basic version of the {@link Receipt} this {@link ReceiptEntry} belongs to. If the id of this
	 *            {@link BasicReceipt} is not set, this {@link ReceiptEntry} will be regareded to be new to the
	 *            database. If set, it must be > 0.
	 * @param productRevision
	 *            {@link ProductRevision} this entry holds information about. At least, the id and revisionNumber must
	 *            be set. This {@link ProductRevision} won't get persisted when this object is persisted. It must be
	 *            separately persisted.
	 * @param count
	 *            Count of this product. Must be > 0.
	 * @param pricePerItem
	 *            Price per item in cent. Must be > 0.
	 */
	public ReceiptEntry(BasicReceipt basicReceipt, ProductRevision productRevision, int count, long pricePerItem) {
		ArgumentUtitlity.checkNull("basicReceipt", basicReceipt);
		ArgumentUtitlity.checkNull("productRevision", productRevision);
		if(count <= 0)
			throw new IllegalArgumentException("count must be greater than 0");
		if(pricePerItem <= 0)
			throw new IllegalArgumentException("pricePerItem must be greater than 0");
		_basicReceipt = basicReceipt;
		_productRevision = productRevision;
		_count = count;
		_pricePerItem = pricePerItem;

		_receiptId = basicReceipt.getId();
		_productId = productRevision.getId();
	}

	@Id
	@Column(name = "receipt_id")
	public Long getReceiptId() {
		// mapping from _basicReceipt.getId() doesn't work with hibernate somewhere...
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


	@Column(name = "product_revision")
	public Integer getProductRevisionNumber() {
		// this method call is necessary because of hibernate lazy init
		// TODO check if this works. especially, if hibernate queries are used on this property...
		return getProductRevision().getRevisionNumber();
	}

	private void setProductRevisionNumber(int productRevision) {
		// hibernate should set this through the productRevision reference
		return;
	}


	@Column(name = "product_count")
	public int getCount() {
		return _count;
	}

	private void setCount(int count) {
		_count = count;
	}


	@Column(name = "price")
	public long getPricePerItem() {
		return _pricePerItem;
	}

	private void setPricePerItem(long price) {
		_pricePerItem = price;
	}


	//
	// not persisted mappings
	//

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumns({ @JoinColumn(name = "product_id", insertable = false, updatable = false),
		@JoinColumn(name = "product_revision", insertable = false, updatable = false) })
		public ProductRevision getProductRevision() {
		return _productRevision;
	}

	private void setProductRevision(ProductRevision productRevision) {
		_productRevision = productRevision;
	}


	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "receipt_id", insertable = false, updatable = false)
	public BasicReceipt getBasicReceipt() {
		return _basicReceipt;
	}

	private void setBasicReceipt(BasicReceipt basicReceipt) {
		_basicReceipt = basicReceipt;
	}


	/**
	 * check this method before simple regeneration, uses getter for productId and revisionNumber
	 */
	@Override
	public String toString() {
		return "ReceiptEntry [_receiptId=" + _basicReceipt.getId() + ", _productId=" + getProductId() + ", _count="
		+ _count + ", _price=" + _pricePerItem + ", _productRevNumber=" + getProductRevisionNumber() + "]";
	}

	/**
	 * check this method before simple regeneration, uses getter for productId and revisionNumber
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _count;
		result = prime * result + (int) (_pricePerItem ^ (_pricePerItem >>> 32));
		result = prime * result + ((getProductId() == null) ? 0 : getProductId().hashCode());
		result = prime * result + ((getProductRevisionNumber() == null) ? 0 : getProductRevisionNumber().hashCode());
		result = prime * result + ((_basicReceipt.getId() == null) ? 0 : _basicReceipt.getId().hashCode());
		return result;
	}

	/**
	 * check this method before simple regeneration, uses getter for productId and revisionNumber
	 */
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
		if (_pricePerItem != other._pricePerItem)
			return false;
		if (getProductId() == null) {
			if (other.getProductId() != null)
				return false;
		} else if (!getProductId().equals(other.getProductId()))
			return false;
		if (getProductRevisionNumber() == null) {
			if (other.getProductRevisionNumber() != null)
				return false;
		} else if (!getProductRevisionNumber().equals(other.getProductRevisionNumber()))
			return false;
		if (_basicReceipt.getId() == null) {
			if (other._basicReceipt.getId() != null)
				return false;
		} else if (!_basicReceipt.getId().equals(other._basicReceipt.getId()))
			return false;
		return true;
	}
}
