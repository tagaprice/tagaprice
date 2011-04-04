package org.tagaprice.client.gwt.shared.entities.productmanagement;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.dump.ICategory;

/**
 * This class is used to send {@link Product} data from client to server and from server to client. It will also be used
 * as model. (Read MVP pattern on <a href="http://code.google.com/webtoolkit/articles/mvp-architecture.html#model" />)
 * 
 */
public class Product extends AEntity<IProduct> implements IProduct {


	private static final long serialVersionUID = 4858431133448109402L;
	private ICategory _category;
	private ArrayList<IPackage> _iPackage = new ArrayList<IPackage>();
	private Unit _unit;

	public Product() {}

	/**
	 * Constructor to update a {@link Product}
	 * @param revisionId
	 * @param title
	 * @param unit
	 */
	public Product(IRevisionId revisionId, String title, ICategory category, Unit unit) {
		super(revisionId, title);
		this._category = category;
		this._unit = unit;
	}

	/**
	 * Constructor to create a new {@link Product}
	 * @param title
	 * @param category
	 * @param quantity
	 */
	public Product(String title, ICategory category, Unit unit) {
		super(title);
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
	public boolean equals(Object o) {
		if(o instanceof Product) {
			Product p = (Product) o;
			return super.equals(o) && this._category.equals(p._category) && this._unit.equals(p._unit);
		} else {
			return false;
		}
	}

	public boolean equals(Product p) {
		return false;
	}

	@Override
	public String toString() {
		String revString;
		if(this.getRevisionId()!= null){
			revString = this.getRevisionId().toString();
		}else{
			revString = "Revision is null";
		}
		String quantitiyString="";

		return "Product: " + this.getTitle() + ", " + revString + ", " + quantitiyString ;
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

}
