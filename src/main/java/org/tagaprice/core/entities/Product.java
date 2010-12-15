package org.tagaprice.core.entities;

import java.util.Date;

public class Product extends Entity {

	public Product() { }

	public Product(Locale locale, Date createdAt, int currentRevisionNumber) {
		super(locale, createdAt, currentRevisionNumber);
	}

	@Override
	public String toString() {
		return "Product derived from " + super.toString();
	}
}
