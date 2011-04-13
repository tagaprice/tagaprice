package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.ASimpleEntity;
import org.tagaprice.shared.entities.dump.Quantity;

public class Package extends ASimpleEntity {
	private static final long serialVersionUID = 1L;

	ArrayList<Integer> _barcode=new ArrayList<Integer>();
	Quantity _iQuantity;
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
	public Package(String id, String revision, Quantity quantity){
		super(id, revision);
		_iQuantity=quantity;
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
}
