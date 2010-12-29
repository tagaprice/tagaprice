package org.tagaprice.core.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;


@Entity
@Table(name="entityrevision")
@SecondaryTables({
	@SecondaryTable(name="productrevision", pkJoinColumns= {
			@PrimaryKeyJoinColumn(name="prod_id", referencedColumnName="ent_id"),
			@PrimaryKeyJoinColumn(name="rev", referencedColumnName="rev")}
	),
	@SecondaryTable(name="product"),
	@SecondaryTable(name="entity")
})
//TODO use RevisionableEntity as superclass and get it to work
public class Product implements Serializable  {
	private static final long serialVersionUID = 1L;
	private Long _id = null;
	private String _title;
	private Locale _locale = null;
	private Date _createdAt = null;
	private Integer _revisionNumber = null;
	private Account _creator = null;
	private Group _group;
	private Category _category;
	private Brand _brand;


	public Product() { }

	public Product(Long id, String title, Locale locale, Date createdAt, Integer revisionNumber, Account creator, Group group,
			Category category, Brand brand) {
		this._id = id;
		this._title = title;
		this._locale = locale;
		this._createdAt = createdAt;
		this._revisionNumber = revisionNumber;
		this._creator = creator;
		this._group = group;
		_category = category;
		_brand = brand;
	}

	@Id
	@Column(name="ent_id")
	public Long getId() {
		return _id;
	}
	@SuppressWarnings("unused")
	private void setId(Long id) {
		this._id = id;
	}

	@Column(name="title")
	public String getTitle() {
		return _title;
	}
	@SuppressWarnings("unused")
	private void setTitle(String title) {
		this._title = title;
	}

	//	@ManyToOne
	//	@JoinColumn(table="entity", name = "locale_id", referencedColumnName="locale_id")
	@Transient
	public Locale getLocale() {
		return _locale;
	}
	@SuppressWarnings("unused")
	private void setLocale(Locale locale) {
		this._locale = locale;
	}

	@Column(name="created_at")
	public Date getCreatedAt() {
		return _createdAt;
	}
	@SuppressWarnings("unused")
	private void setCreatedAt(Date createdAt) {
		this._createdAt = createdAt;
	}

	@Id
	@Column(name="rev")
	public Integer getRevisionNumber() {
		return _revisionNumber;
	}
	@SuppressWarnings("unused")
	private void setRevisionNumber(Integer revisionNumber) {
		this._revisionNumber = revisionNumber;
	}

	//	@ManyToOne
	//	@JoinColumn(table="entity", name = "creator")
	@Transient
	public Account getCreator() {
		return _creator;
	}
	@SuppressWarnings("unused")
	private void setCreator(Account creator) {
		this._creator = creator;
	}

	@Transient
	public Group getGroup() {
		return _group;
	}
	@SuppressWarnings("unused")
	private void setGroup(Group group) {
		this._group = group;
	}

	//
	// product specific
	//


	//	@ManyToOne
	//	@JoinColumn(name = "type_id")
	@Transient
	public Category getCategory() {
		return _category;
	}
	@SuppressWarnings("unused")
	private void setCategory(Category category) {
		_category = category;
	}

	//	@ManyToOne
	//	@JoinColumn(name="brand_id")
	@Transient
	public Brand getBrand() {
		return _brand;
	}
	@SuppressWarnings("unused")
	private void setBrand(Brand brand) {
		_brand = brand;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((_createdAt == null) ? 0 : _createdAt.hashCode());
		result = prime * result + ((_id == null) ? 0 : _id.hashCode());
		result = prime * result + ((_revisionNumber == null) ? 0 : _revisionNumber.hashCode());
		result = prime * result + ((_title == null) ? 0 : _title.hashCode());
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
		Product other = (Product) obj;
		if (_createdAt == null) {
			if (other._createdAt != null)
				return false;
		} else if (!_createdAt.equals(other._createdAt))
			return false;
		if (_id == null) {
			if (other._id != null)
				return false;
		} else if (!_id.equals(other._id))
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
		return true;
	}

	@Override
	public String toString() {
		return "Product derived from " + super.toString();
	}
}
