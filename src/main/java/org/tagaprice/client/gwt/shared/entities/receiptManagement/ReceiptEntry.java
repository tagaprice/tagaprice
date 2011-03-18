package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import org.tagaprice.client.gwt.shared.entities.productmanagement.*;

public class ReceiptEntry implements IReceiptEntry {


	private static final long serialVersionUID = -7990234621338289392L;
	private IPackage _package;

	private IPrice _price;
	private IReceipt _receipt;

	public ReceiptEntry() {
	}


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
