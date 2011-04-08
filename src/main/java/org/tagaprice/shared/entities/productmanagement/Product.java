package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.*;
import org.tagaprice.shared.entities.dump.Category;

/**
 * This class is used to send {@link Product} data from client to server and from server to client. It will also be used
 * as model. (Read MVP pattern on <a href="http://code.google.com/webtoolkit/articles/mvp-architecture.html#model" />)
 * 
 */
public class Product extends AEntity {


	private static final long serialVersionUID = 4858431133448109402L;
	private Category _category;
	private ArrayList<Package> _iPackage = new ArrayList<Package>();
	private Unit _unit;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Product() {}

	/**
	 * <b>NEW</b>
	 * Constructor to create a new {@link Product}
	 * @param title Product Title
	 * @param category Product category
	 * @param Unit Product unit
	 */
	public Product(String title, Category category, Unit unit) {
		super(title);
		this._category = category;
		this._unit = unit;

	}

	/**
	 * <b>UPDATE and GET</b>
	 * Constructor to update and GET a {@link Product}
	 * @param revisionId the product revision
	 * @param title Product Title
	 * @param category Product category
	 * @param Unit Product unit
	 */
	public Product(String productId, String revision, String title, Category category, Unit unit) {
		super(productId, revision, title);
		this._category = category;
		this._unit = unit;
	}

	public void setCategory(Category category) {
		_category=category;

	}

	public Category getCategory() {
		return _category;
	}

	public void addPackage(Package ipackage) {
		ipackage.setProduct(this);
		_iPackage.add(ipackage);
	}

	public void setPackages(ArrayList<Package> iPackage) {
		_iPackage.clear();
		for (Package p: iPackage) {
			p.setProduct(this);
			_iPackage.add(p);
		}

	}

	public ArrayList<Package> getPackages() {
		return _iPackage;
	}

	public void setUnit(Unit unit) {
		_unit=unit;

	}

	public Unit getUnit() {
		return _unit;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [_category=" + _category + ", _unit=" + _unit + "]";
	}



}
