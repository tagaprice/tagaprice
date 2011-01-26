package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.*;

public class ReceiptEntry implements IReceiptEntry{


	private static final long serialVersionUID = -7990234621338289392L;

	private int _quantity;
	private long _price;
	private IProduct _product;
	private IReceipt _receipt;
	private IRevisionId _productId;
	private IRevisionId _receiptId;

	public ReceiptEntry(){
		super();
	}
	/**
	 * Initializes a new {@link Receipt}
	 * @param quantity must be >0
	 * @param price    must be >0
	 * @param product
	 * @param receipt
	 * @param productId
	 * @param receiptId
	 */
	public ReceiptEntry(int quantity, long price, IProduct product, IReceipt receipt, IRevisionId productId,
			IRevisionId receiptId) {
		super();
		_quantity = quantity;
		_price = price;
		_product = product;
		_receipt = receipt;
		_productId = productId;
		_receiptId = receiptId;
	}

	@Override
	public long getPrice() {
		return _price;
	}
	@Override
	public IProduct getProduct() {
		return _product;
	}
	@Override
	public int getQuantity() {
		return _quantity;
	}
	@Override
	public IReceipt getReceipt() {
		return _receipt;
	}
	@Override
	public void setPrice(long price) {
		_price = price;

	}
	@Override
	public void setProduct(IProduct product) {
		_product = product;

	}
	@Override
	public void setQuantity(int quantity) {
		_quantity = quantity;

	}
	@Override
	public void setReceipt(IReceipt receipt) {
		_receipt = receipt;

	}
	@Override
	public IRevisionId getProductId() {
		return getProduct().getRevisionId();
	}
	@Override
	public IRevisionId getReceiptId() {
		return getReceipt().getRevisionId();
	}
	@Override
	public void setProductId(IRevisionId productId) {
		_productId = productId;

	}
	@Override
	public void setReceiptId(IRevisionId receiptId) {
		_receiptId = receiptId;

	}

	@Override
	public String toString() {
		return "ReceiptEntry [_price=" + _price + ", _product=" + _product + ", _productId=" + _productId
		+ ", _quantity=" + _quantity + ", _receipt=" + _receipt + ", _receiptId=" + _receiptId + "]";
	}




}
