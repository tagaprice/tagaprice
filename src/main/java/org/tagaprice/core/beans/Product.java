package org.tagaprice.core.beans;

import java.util.Date;

public class Product extends Entity {

	public Product() { }

//	public Product(long id, int localeId, Date createdAt, int currentRevisionNumber) {
//		super(id, localeId, createdAt, currentRevisionNumber);
//	}
	
	public Product(int localeId, Date createdAt, int currentRevisionNumber) {
		super(localeId, createdAt, currentRevisionNumber);
	}

	@Override
	public String toString() {
		return "Product [getLocaleId()=" + getLocaleId() + ", getId()="
				+ getId() + ", getCreatedAt()=" + getCreatedAt()
				+ ", getCurrentRevision()=" + getCurrentRevision()
				+ ", getCurrentRevisionNumber()=" + getCurrentRevisionNumber()
				+ "]";
	}
}
