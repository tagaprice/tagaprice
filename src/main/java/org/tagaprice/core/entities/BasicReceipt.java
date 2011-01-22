package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.tagaprice.server.helper.ArgumentUtitlity;

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
	private Date _createdAt;

	protected BasicReceipt() {
	}

	public BasicReceipt(Long receiptId, BasicShop basicShop, Date createdAt) {
		ArgumentUtitlity.checkNull("receiptId", receiptId);
		ArgumentUtitlity.checkNull("basicShop", basicShop);
		ArgumentUtitlity.checkNull("createdAt", createdAt);
		
		_receiptId = receiptId;
		_basicShop = basicShop;
		_createdAt = createdAt;
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
	
	@Column(name = "created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	private void setDate(Date date) {
		_createdAt = date;
	}

	@Override
	public String toString() {
		return "BasicReceipt [_receiptId=" + _receiptId + ", _basicShop=" + _basicShop + "]";
	}

	
}
