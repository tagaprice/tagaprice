package org.tagaprice.shared.entities.productmanagement;

import java.util.ArrayList;
import java.util.List;

import org.svenson.JSONProperty;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.accountmanagement.User;
import org.tagaprice.shared.entities.categorymanagement.Category;

/**
 * This class is used to send {@link Product} data from client to server and from server to client. It will also be used
 * as model. (Read MVP pattern on <a href="http://code.google.com/webtoolkit/articles/mvp-architecture.html#model" />)
 * 
 */
public class Product extends Document {
	private static final long serialVersionUID = 1L;
	private Category _category;
	private ArrayList<Package> _iPackage = new ArrayList<Package>();
	private Unit _unit;

	/**
	 * This constructor is used by the serialization algorithm
	 */
	public Product() {
	}

	/**
	 * <b>NEW</b>
	 * Constructor to create a new {@link Product}
	 * 
	 * @param creator Creator of the current document revision
	 * @param title Product Title
	 * @param category Product category
	 * @param unit Product unit
	 */
	public Product(User creator, String title, Category category, Unit unit) {
		super(creator, title);
		this._category = category;
		this._unit = unit;

	}

	/**
	 * <b>UPDATE and GET</b>
	 * Constructor to update and GET a {@link Product}
	 * 
	 * @param creator Creator of the current document revision
	 * @param revision The product revision
	 * @param title Product Title
	 * @param category Product category
	 * @param unit Product unit
	 */
	public Product(User creator, String productId, String revision, String title, Category category, Unit unit) {
		super(creator, productId, revision, title);
		this._category = category;
		this._unit = unit;
	}

	/**
	 * Add one {@link Package} to the Product.
	 * 
	 * @param ipackage
	 *            that will be added to the {@link Product}
	 */
	@JSONProperty(ignore = true)
	public void addPackage(Package ipackage) {
		ipackage.setProduct(this);
		_iPackage.add(ipackage);
	}

	@Override
	public boolean equals(Object otherObject) {
		boolean rc = true;

		if (otherObject instanceof Product) {
			Product other = (Product) otherObject;
			if (!super.equals(other))
				rc = false;
			else if (!_equals(_category, other._category))
				rc = false;
			else if (!_equalArrays(_iPackage, other._iPackage))
				rc = false;
			else if (!_equals(_unit, other._unit))
				rc = false;
		} else
			rc = false;

		return rc;
	}

	/**
	 * Returns the {@link Category} which this {@link Product} depends from.
	 * 
	 * @return Returns the {@link Category} which this {@link Product} depends from.
	 */
	@JSONProperty(ignore = true)
	public Category getCategory() {
		return _category;
	}

	/**
	 * Returns CategoryId or null
	 * @return CategoryId or null
	 */
	@JSONProperty(value = "categoryId", ignoreIfNull = true)
	public String getCategoryId() {
		String rc = null;
		if (getCategory() != null)
			rc = getCategory().getId();
		return rc;
	}

	/**
	 * Return all Packages includes in a {@link Product}
	 * 
	 * @return all Packages includes in a {@link Product}
	 */
	@JSONProperty(ignore = true)
	public ArrayList<Package> getPackages() {
		return _iPackage;
	}

	/**
	 * The {@link Unit} in which a {@link Product} can be
	 * bought.
	 * 
	 * @return the {@link Unit} in which a {@link Product} can be
	 *         bought
	 */
	@JSONProperty(ignore = true)
	public Unit getUnit() {
		return _unit;
	}

	/**
	 * Returns current unit id or null
	 * @return unitId or null
	 */
	@JSONProperty(value = "unitId", ignoreIfNull = true)
	public String getUnitId() {
		String rc = null;
		if (getUnit() != null)
			rc = getUnit().getId();
		return rc;
	}

	/**
	 * Sets the depending {@link Category} for a product.
	 * 
	 * @param category
	 *            Is the depending {@link Category} for a product
	 */
	public void setCategory(Category category) {
		_category = category;

	}

	/**
	 * Internal method necessary for the CouchDB injection to work
	 * @param categoryId Category ID
	 */
	public void setCategoryId(String categoryId) {
		setCategory(new Category(null, categoryId, null, null, null));
	}

	/**
	 * Set some {@link Package} to the Product. All included products will be deleted.
	 * 
	 * @param ipackage
	 *            that will be set to the {@link Product}
	 */
	public void setPackages(List<Package> iPackage) {
		_iPackage.clear();
		for (Package p : iPackage) {
			p.setProduct(this);
			_iPackage.add(p);
		}

	}

	/**
	 * Sets the {@link Unit} which a {@link Product} can have.
	 * 
	 * @param unit
	 *            the {@link Unit} which a {@link Product} can have.
	 */
	public void setUnit(Unit unit) {
		_unit = unit;

	}

	/**
	 * Internal method necessary for the CouchDB injection to work
	 * @param unitId unit ID
	 */
	public void setUnitId(String unitId) {
		setUnit(new Unit(null, unitId, null, null, null,1.0));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Product [" + super.toString() + ", category=" + _category + ", unit=" + _unit + "]";
	}

}
