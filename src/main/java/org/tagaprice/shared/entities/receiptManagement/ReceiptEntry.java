package org.tagaprice.shared.entities.receiptManagement;

import org.tagaprice.shared.entities.ASimpleEntity;
import org.tagaprice.shared.entities.productmanagement.Package;

public class ReceiptEntry extends ASimpleEntity {
	private static final long serialVersionUID = 1L;

	private Package _package;
	private Price _price;
	private Receipt _receipt;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public ReceiptEntry() {}

	/**
	 * <b>NEW</b>
	 * Create a new ReceiptEntry. Method setReceipt() must be called
	 * @param price the price of the {@link ReceiptEntry}
	 * @param ipackage the related package
	 */
	public ReceiptEntry(Price price, Package ipackage) {
		setPrice(price);
		setPackage(ipackage);
	}

	public Package getPackage() {
		return _package;
	}

	public Price getPrice() {
		return _price;
	}

	public Receipt getReceipt() {
		return _receipt;
	}

	public void setPackage(Package ipackage) {
		_package = ipackage;
	}

	public void setPrice(Price price) {
		_price = price;
	}

	public void setReceipt(Receipt receipt) {
		_receipt = receipt;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReceiptEntry [_package=" + _package + ", _receipt=" + _receipt + ", _price=" + _price + "]";
	}



}
