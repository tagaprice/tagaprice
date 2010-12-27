package org.tagaprice.core.entities;

import java.util.Date;

public class Product extends Entity {

	private Category _category;
	private Brand _brand;

	public Product() { }

	public Product(Long id, String title, Locale locale, Date createdAt, int currentRevisionNumber, Account creator, Group group,
			Category category, Brand brand) {
		super(id, title, locale, createdAt, currentRevisionNumber, creator,group);
		_category = category;
		_brand = brand;
	}

	@Override
	public String toString() {
		return "Product derived from " + super.toString();
	}
}
