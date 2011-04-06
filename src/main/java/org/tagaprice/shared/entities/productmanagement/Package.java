package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.ASEntity;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.dump.IQuantity;

public class Package extends ASEntity<IPackage> implements IPackage {

	ArrayList<Integer> _barcode=new ArrayList<Integer>();
	IQuantity _iQuantity;
	IRevisionId _iRevisionId;
	IProduct _product;

	/**
	 * Necessary for Serialisation
	 */
	public Package() {
		super();
	}


	/**
	 * <b>NEW</b>
	 * Creates an new Package.
	 * @param quantity the current quantity of a package.
	 */
	public Package(IQuantity quantity){
		super(null);
		_iQuantity=quantity;
	}

	/**
	 * <b>UPDATE and GET</b>
	 * Get or Update Package.
	 * @param revisionId current revisionId.
	 * @param quantity the current quantity of a package.
	 */
	public Package(IRevisionId revisionId, IQuantity quantity){
		_iQuantity=quantity;
		_iRevisionId=revisionId;
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
	public IProduct getProduct() {
		return _product;
	}

	@Override
	public IQuantity getQuantity() {
		return _iQuantity;
	}


	@Override
	public void setProduct(IProduct product) {
		_product=product;
	}


	@Override
	public void setQuantity(IQuantity quantity) {
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
