package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.*;
import org.tagaprice.shared.entities.categorymanagement.Category;

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

	@JSONProperty(ignore=true)
	public void setCategory(Category category) {
		_category=category;

	}

	@JSONProperty(ignore=true)
	public Category getCategory() {
		return _category;
	}
	
	@JSONProperty(value="categoryId", ignoreIfNull=true)
	public String getCategoryId() {
		String rc = null;
		if (getCategory() != null) rc = getCategory().getId();
		return rc;
	}

	public void setCategoryId(String categoryId) {
		_category = new Category(categoryId, null, null, null);
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

	@JSONProperty(ignore=true)
	public ArrayList<Package> getPackages() {
		return _iPackage;
	}

	public void setUnit(Unit unit) {
		_unit=unit;

	}

	@JSONProperty(ignore=true)
	public Unit getUnit() {
		return _unit;
	}
	
	@JSONProperty(value="unitId", ignoreIfNull=true)
	public String getUnitId() {
		String rc = null;
		if (getUnit() != null) rc = getUnit().getId();
		return rc;
	}

	public boolean equals(Object otherObject) {
		boolean rc = true;
		
		if (otherObject instanceof Product) {
			Product other = (Product) otherObject;
			if (!super.equals(other)) rc = false;
			else if (!_equals(_category, other._category)) rc = false;
			else if (!_equalArrays(_iPackage, other._iPackage)) rc = false;
			else if (!_equals(_unit, other._unit)) rc = false;
		}
		else rc = false;
		
		return rc;
	}

}
