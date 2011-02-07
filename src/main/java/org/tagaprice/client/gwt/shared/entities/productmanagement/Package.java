package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

public class Package implements IPackage {

	IQuantity _iQuantity;
	ArrayList<Integer> _barcode=new ArrayList<Integer>();

	/**
	 * Necessary for Serialisation
	 */
	public Package() {
	}


	public Package(IQuantity quantity){
		_iQuantity=quantity;
	}


	@Override
	public void setQuantity(IQuantity quantity) {
		_iQuantity=quantity;
	}

	@Override
	public IQuantity getQuantity() {
		return _iQuantity;
	}

	@Override
	public void addBarcode(int barcode) {
		_barcode.add(barcode);
	}

	@Override
	public void addBarcodes(ArrayList<Integer> barcode) {
		_barcode.addAll(barcode);
	}

	@Override
	public ArrayList<Integer> getBarcodes() {
		return _barcode;
	}


}
