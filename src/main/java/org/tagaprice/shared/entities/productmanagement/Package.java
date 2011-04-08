package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.ASEntity;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.dump.Quantity;

public class Package extends ASEntity {
	private static final long serialVersionUID = 1L;

	ArrayList<Integer> _barcode=new ArrayList<Integer>();
	Quantity _iQuantity;
	RevisionId _iRevisionId;
	Product _product;

	/**
	 * Necessary for serialization
	 */
	public Package() {
		super();
	}


	/**
	 * <b>NEW</b>
	 * Creates an new Package.
	 * @param quantity the current quantity of a package.
	 */
	public Package(Quantity quantity) {
		super();
		_iQuantity=quantity;
	}

	/**
	 * <b>UPDATE and GET</b>
	 * Get or Update Package.
	 * @param revisionId current revisionId.
	 * @param quantity the current quantity of a package.
	 */
	public Package(RevisionId revisionId, Quantity quantity){
		_iQuantity=quantity;
		_iRevisionId=revisionId;
	}

	public void addBarcode(int barcode) {
		_barcode.add(barcode);
	}

	public void addBarcodes(ArrayList<Integer> barcode) {
		_barcode.addAll(barcode);
	}

	public ArrayList<Integer> getBarcodes() {
		return _barcode;
	}

	public Product getProduct() {
		return _product;
	}

	public Quantity getQuantity() {
		return _iQuantity;
	}

	public void setProduct(Product product) {
		_product=product;
	}

	public void setQuantity(Quantity quantity) {
		_iQuantity=quantity;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Package [_barcode=" + _barcode + ", _iQuantity=" + _iQuantity + ", _iRevisionId=" + _iRevisionId+"]";
	}



}
