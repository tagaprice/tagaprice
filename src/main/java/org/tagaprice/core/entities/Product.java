package org.tagaprice.core.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

@Entity
@Table(name="productrevision")
@PrimaryKeyJoinColumns({
	@PrimaryKeyJoinColumn(name="prod_id", referencedColumnName="ent_id"),
	@PrimaryKeyJoinColumn(name="rev", referencedColumnName="rev")
})
@SecondaryTable(name="producttype", pkJoinColumns={
		@PrimaryKeyJoinColumn(name="prod_id", referencedColumnName="prod_id")
})
public class Product extends RevisionableEntity {
	private Category _category;
	private Brand _brand;

	public Product() { }

	public Product(Long id, String title, Locale locale, Date createdAt, int currentRevisionNumber, Account creator, Group group,
			Category category, Brand brand) {
		super(id, title, locale, createdAt, currentRevisionNumber, creator,group);
		_category = category;
		_brand = brand;
	}


	@ManyToOne
	@JoinColumn(name = "type_id")
	public Category getCategory() {
		return _category;
	}
	@SuppressWarnings("unused")
	private void setCategory(Category category) {
		_category = category;
	}

	@ManyToOne
	@JoinColumn(name="brand_id")
	public Brand getBrand() {
		return _brand;
	}
	@SuppressWarnings("unused")
	private void setBrand(Brand brand) {
		_brand = brand;
	}


	@Override
	public String toString() {
		return "Product derived from " + super.toString();
	}
}
