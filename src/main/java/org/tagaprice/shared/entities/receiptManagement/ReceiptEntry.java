package org.tagaprice.shared.entities.receiptManagement;

import org.tagaprice.shared.entities.productmanagement.*;

public class ReceiptEntry implements IReceiptEntry {


	private IPackage _package;
	private IPrice _price;
	private IReceipt _receipt;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public ReceiptEntry() {}

	/**
	 * <b>NEW</b>
	 * Create a new ReceiptEntry. Method setReceipt() must be called
	 * @param price the price of the {@link IReceiptEntry}
	 * @param ipackage the related package
	 */
	public ReceiptEntry(IPrice price, IPackage ipackage) {
		setPrice(price);
		setPackage(ipackage);
	}

	@Override
	public IPackage getPackage() {
		return _package;
	}

	@Override
	public IPrice getPrice() {
		return _price;
	}

	@Override
	public IReceipt getReceipt() {
		return _receipt;
	}

	@Override
	public void setPackage(IPackage ipackage) {
		_package = ipackage;
	}

	@Override
	public void setPrice(IPrice price) {
		_price = price;
	}

	@Override
	public void setReceipt(IReceipt receipt) {
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
