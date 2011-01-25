package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import org.tagaprice.client.gwt.shared.entities.AEntity;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Product;

public class ReceiptEntry extends AEntity<IReceiptEntry> implements IReceiptEntry{


	private static final long serialVersionUID = -7990234621338289392L;

	private int _quantity;
	private double _price;
	private Product _product;
	private Receipt _receipt;
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
	public ReceiptEntry(int quantity, double price, Product product, Receipt receipt, IRevisionId productId,
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
	public int getPrice() {
		return _price;
	}
	@Override
	public Product getProduct() {
		return _product;
	}
	@Override
	public int getQuantity() {
		return _quantity;
	}
	@Override
	public Receipt getReceipt() {
		return _receipt;
	}
	@Override
	public void setPrice(int price) {
		_price = price;

	}
	@Override
	public void setProduct(Product product) {
		_product = product;

	}
	@Override
	public void setQuantity(int quantity) {
		_quantity = quantity;

	}
	@Override
	public void setReceipt(Receipt receipt) {
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
	public IReceiptEntry copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString() {
		return "ReceiptEntry [_price=" + _price + ", _product=" + _product + ", _productId=" + _productId
		+ ", _quantity=" + _quantity + ", _receipt=" + _receipt + ", _receiptId=" + _receiptId + "]";
	}




}
