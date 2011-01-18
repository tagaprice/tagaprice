package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.SecondaryTables;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * <p>
 * This class represents a specific revision of a product. For every new revision of a product, such a
 * {@link ProductRevision} is added to the set of ProductRevisions of the {@link Product}.
 * </p>
 * 
 * <p>
 * A {@link ProductRevision} has the following properties:
 * <ul>
 * <li>Id: primary identifier in the database of the associated {@link Product} and part of the identifier in the
 * database for this {@link ProductRevision}</li>
 * <li>revisionNumber: revision number and part of the identifier in the database</li>
 * <li>title: name of the product</li>
 * <li>createdAt: Date this revision was created</li>
 * <li>creator: {@link Account} who created this revision</li>
 * <li>unit: {@link Unit} in which this product is measured</li>
 * <li>amount: amount in which this product is sold</li>
 * <li>category: a {@link Category} this product is associated with</li>
 * <li>imageURL: the location of a picture of this product, as it is (normally) sold</li>
 * </ul>
 * </p>
 * 
 * <p>
 * TODO This class may should be immutable.
 * </p>
 * 
 * @author haja
 * @author forste
 * 
 */
@Entity
@Table(name = "productrevision")
@SecondaryTables({ @SecondaryTable(name = "entityrevision") })
@SuppressWarnings("unused")
public class ProductRevision implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long _id = null;
	private Integer _revisionNumber = null;
	private String _title;
	private Date _createdAt = null;
	private Account _creator = null;
	private Unit _unit;
	private Double _amount;
	private Category _category;
	private String _imageURL;

	/**
	 * this constructor is need for hibernate.
	 */
	protected ProductRevision() {
	}

	/**
	 * Initialize a new {@link ProductRevision}.
	 * 
	 * @param id
	 *            Id of the associated product. Can be null if the associated {@link Product}s id is null. TODO check
	 *            this condition, maybe this can always be null?
	 * @param revisionNumber
	 *            Revision number of this product. To create a new revision of a product, this must be set to (&lt;value
	 *            of old revision of product&gt; + 1). If this product is new, this must be set to 1.
	 * @param title
	 *            Name of the product.
	 * @param createdAt
	 *            Date this revision was created.
	 * @param creator
	 *            {@link Account} who created this revision.
	 * @param unit
	 *            {@link Unit} in which this product is measured.
	 * @param amount
	 *            Amount in which this product is sold.
	 * @param category
	 *            {@link Category} this product is associated with. May be null if no category is assigned.
	 * @param imageURL
	 *            The location of a picture of this product, as it is (normally) sold. TODO can this be null?
	 */
	public ProductRevision(Long id, Integer revisionNumber, String title, Date createdAt, Account creator, Unit unit,
			Double amount, Category category, String imageURL) {
		_id = id;
		_title = title;
		_createdAt = createdAt;
		_revisionNumber = revisionNumber;
		_creator = creator;
		_category = category;
		_unit = unit;
		_amount = amount;
		_imageURL = imageURL;
	}

	@Id
	@Column(name = "ent_id")
	public Long getId() {
		return _id;
	}

	/**
	 * TODO this is public due to service having to set the id if not present, should not be public probably
	 * This violates immutability of this class.
	 */
	public void setId(Long id) {
		this._id = id;
	}


	@Id
	@Column(name = "rev")
	public Integer getRevisionNumber() {
		return _revisionNumber;
	}

	private void setRevisionNumber(Integer revisionNumber) {
		this._revisionNumber = revisionNumber;
	}


	@Column(table = "entityrevision", name = "title")
	public String getTitle() {
		return _title;
	}

	private void setTitle(String title) {
		this._title = title;
	}


	@Column(table = "entityrevision", name = "created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}

	private void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}


	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(table = "entityrevision", name = "creator")
	public Account getCreator() {
		return _creator;
	}

	private void setCreator(Account creator) {
		this._creator = creator;
	}


	//
	// product specific
	//


	@Enumerated(EnumType.STRING)
	public Unit getUnit() {
		return _unit;
	}
	private void setUnit(Unit unit) {
		_unit = unit;
	}


	public Double getAmount() {
		return _amount;
	}
	private void setAmount(double amount) {
		_amount = amount;
	}


	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	public Category getCategory() {
		return _category;
	}
	private void setCategory(Category category) {
		_category = category;
	}


	@Column(name = "imageurl")
	public String getImageURL() {
		return _imageURL;
	}
	private void setImageURL(String imageURL) {
		_imageURL = imageURL;
	}


	@Override
	public String toString() {
		return "ProductRevision [_id=" + _id + ", _revisionNumber=" + _revisionNumber + ", _title=" + _title
		+ ", _createdAt=" + _createdAt + ", _creator=" + _creator + ", _unit=" + _unit + ", _amount=" + _amount
		+ ", _category=" + _category + ", _imageURL=" + _imageURL + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_amount == null) ? 0 : _amount.hashCode());
		result = prime * result + ((_category == null) ? 0 : _category.hashCode());
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + ((_creator == null) ? 0 : _creator.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + ((_imageURL == null) ? 0 : _imageURL.hashCode());
		result = prime * result + ((_revisionNumber == null) ? 0 : _revisionNumber.hashCode());
		result = prime * result + ((_title == null) ? 0 : _title.hashCode());
		result = prime * result + ((_unit == null) ? 0 : _unit.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductRevision other = (ProductRevision) obj;
		if (_amount == null) {
			if (other._amount != null)
				return false;
		} else if (!_amount.equals(other._amount))
			return false;
		if (_category == null) {
			if (other._category != null)
				return false;
		} else if (!_category.equals(other._category))
			return false;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_creator == null) {
			if (other._creator != null)
				return false;
		} else if (!_creator.equals(other._creator))
			return false;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
			return false;
		if (_imageURL == null) {
			if (other._imageURL != null)
				return false;
		} else if (!_imageURL.equals(other._imageURL))
			return false;
		if (_revisionNumber == null) {
			if (other._revisionNumber != null)
				return false;
		} else if (!_revisionNumber.equals(other._revisionNumber))
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		if (_unit == null) {
			if (other._unit != null)
				return false;
		} else if (!_unit.equals(other._unit))
			return false;
		return true;
	}


	/**
	 * Compares this with given obj. Only fields valuable to business logic, e.g. identifiers are not valuable to
	 * business logic, are compared.
	 * Returns false if any of the compared fields mismatch or true otherwise.
	 */
	public boolean businessEquals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProductRevision other = (ProductRevision) obj;
		if (_amount == null) {
			if (other._amount != null)
				return false;
		} else if (!_amount.equals(other._amount))
			return false;
		if (_category == null) {
			if (other._category != null)
				return false;
		} else if (!_category.equals(other._category))
			return false;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_creator == null) {
			if (other._creator != null)
				return false;
		} else if (!_creator.equals(other._creator))
			return false;
		if (_imageURL == null) {
			if (other._imageURL != null)
				return false;
		} else if (!_imageURL.equals(other._imageURL))
			return false;
		if (_revisionNumber == null) {
			if (other._revisionNumber != null)
				return false;
		} else if (!_revisionNumber.equals(other._revisionNumber))
			return false;
		if (_title == null) {
			if (other._title != null)
				return false;
		} else if (!_title.equals(other._title))
			return false;
		if (_unit == null) {
			if (other._unit != null)
				return false;
		} else if (!_unit.equals(other._unit))
			return false;
		return true;
	}

}
