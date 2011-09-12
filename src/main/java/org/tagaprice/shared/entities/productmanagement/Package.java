package org.tagaprice.shared.entities.productmanagement;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Quantity;

public class Package extends Document {
	private static final long serialVersionUID = 1L;

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
	 * @param creator Creator of the current document revision
	 * @param revisionId current revisionId.
	 * @param quantity the current quantity of a package.
	 */
	public Package(String creatorId, String id, String revision, Quantity quantity){
		super(creatorId, id, revision, null);
		_iQuantity=quantity;
	}


	/**
	 * Returns the related {@link IProduct}
	 * @return the related {@link IProduct}
	 */
	@JSONProperty(ignore = true)
	public Product getProduct() {
		return _product;
	}

	public String getProductId(){
		if(_product!=null)return _product.getId();
		return null;
	}

	public void setProductId(String productId){
		setProduct(new Product(null, productId, null, null, null, null));
	}

	/**
	 * Returns the {@link IQuantity} of an {@link IPackage}
	 * 
	 * @return the {@link IQuantity} of an {@link IPackage}
	 */

	public Quantity getQuantity() {
		return _iQuantity;
	}

	/**
	 * Set the related {@link Product}
	 * @param product the related {@link Product}
	 */
	public void setProduct(Product product) {
		_product=product;
	}

	/**
	 * Set the {@link IQuantity} which is represented by an Quantity and an Unit.
	 * 
	 * @param qantity
	 *            is represented by an Quantity and an Unit
	 */
	public void setQuantity(Quantity quantity) {
		_iQuantity=quantity;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Package [_iQuantity=" + _iQuantity + "]";
	}





}
