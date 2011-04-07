package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.shared.entities.*;
import org.tagaprice.shared.entities.dump.ICategory;

/**
 * This class is used to send {@link Product} data from client to server and from server to client. It will also be used
 * as model. (Read MVP pattern on <a href="http://code.google.com/webtoolkit/articles/mvp-architecture.html#model" />)
 * 
 */
public class Product extends AEntity implements IProduct {


	private static final long serialVersionUID = 4858431133448109402L;
	private ICategory _category;
	private ArrayList<IPackage> _iPackage = new ArrayList<IPackage>();
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
	public Product(String title, ICategory category, Unit unit) {
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
	public Product(String productId, String revision, String title, ICategory category, Unit unit) {
		super(productId, revision, title);
		this._category = category;
		this._unit = unit;
	}



	@Override
	public void setCategory(ICategory category) {
		_category=category;

	}

	@Override
	public ICategory getCategory() {
		return _category;
	}



	@Override
	public void addPackage(IPackage ipackage) {
		ipackage.setProduct(this);
		_iPackage.add(ipackage);
	}

	@Override
	public void setPackages(ArrayList<IPackage> iPackage) {
		_iPackage.clear();
		for(IPackage p:iPackage){
			p.setProduct(this);
			_iPackage.add(p);
		}

	}

	@Override
	public ArrayList<IPackage> getPackages() {
		return _iPackage;
	}

	@Override
	public void setUnit(Unit unit) {
		_unit=unit;

	}

	@Override
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
