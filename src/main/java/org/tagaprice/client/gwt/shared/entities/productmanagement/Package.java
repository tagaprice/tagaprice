package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;

public class Package implements IPackage {

	IQuantity _iQuantity;
	IRevisionId _iRevisionId;
	ArrayList<Integer> _barcode=new ArrayList<Integer>();

	/**
	 * Necessary for Serialisation
	 */
	public Package() {
	}


	/**
	 * Creates an new Package.
	 * @param quantity the current quantity of a package.
	 */
	public Package(IQuantity quantity){
		_iQuantity=quantity;
	}

	/**
	 * Get or Update Package.
	 * @param revisionId current revisionId.
	 * @param quantity the current quantity of a package.
	 */
	public Package(IRevisionId revisionId, IQuantity quantity){
		_iQuantity=quantity;
		_iRevisionId=revisionId;
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


	@Override
	public IRevisionId getRevisionId() {
		return _iRevisionId;
	}


	@Override
	public void setRevisionId(IRevisionId revisionId) {
		_iRevisionId=revisionId;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Package [_iQuantity=" + _iQuantity + ", _iRevisionId=" + _iRevisionId + ", _barcode=" + _barcode + "]";
	}



}
