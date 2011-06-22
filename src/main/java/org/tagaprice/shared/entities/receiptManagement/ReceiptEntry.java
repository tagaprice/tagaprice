package org.tagaprice.shared.entities.receiptManagement;

import java.io.Serializable;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.productmanagement.Package;

public class ReceiptEntry implements Serializable {
	private static final long serialVersionUID = 1L;

	private Package _package;
	private Price _price;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public ReceiptEntry() {
		super();
	}

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

	/**
	 * @return the related {@link IPackage}
	 */
	@JSONProperty(ignore = true)
	public Package getPackage() {
		return _package;
	}

	public String getPackageId(){
		if(_package==null)return null;
		return _package.getId();
	}

	/**
	 * returns the price per {@link Currency} in cent
	 * @return per {@link Currency}  in cent
	 */
	public Price getPrice() {
		return _price;
	}


	/**
	 * defines the {@link Package} (Product)
	 * @param prackage the {@link Package} (Product)
	 */
	@JSONProperty(ignore = true)
	public void setPackage(Package ipackage) {
		_package = ipackage;
	}

	public void setPackageId(String packageId){
		_package = new Package(null, packageId, null, null);
	}

	/**
	 * sets the price per {@link Currency}  in cent
	 * @param price  per {@link Currency}  in cent
	 */
	public void setPrice(Price price) {
		_price = price;
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReceiptEntry [_package=" + _package + ", _price=" + _price + "]";
	}




}
