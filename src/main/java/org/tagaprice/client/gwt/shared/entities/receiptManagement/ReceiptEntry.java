package org.tagaprice.client.gwt.shared.entities.receiptManagement;

import org.tagaprice.client.gwt.shared.entities.productmanagement.*;

public class ReceiptEntry implements IReceiptEntry{


	private static final long serialVersionUID = -7990234621338289392L;

	private IPackage _package;
	private IReceipt _receipt;
	private IPrice _price;


	public ReceiptEntry() {
	}

	public ReceiptEntry(IReceipt receipt, IPrice price, IPackage ipackage){
		setReceipt(receipt);
		setPrice(price);
		setPackage(ipackage);
	}

	@Override
	public void setReceipt(IReceipt receipt) {
		_receipt=receipt;

	}
	@Override
	public void setPrice(IPrice price) {
		_price=price;
	}
	@Override
	public void setPackage(IPackage ipackage) {
		_package=ipackage;
	}
	@Override
	public IPackage getPackage() {
		return _package;
	}
	@Override
	public IReceipt getReceipt() {
		return _receipt;
	}
	@Override
	public IPrice getPrice() {
		return _price;
	}






}
