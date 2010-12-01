package org.tagaprice.core.beans;

import java.util.Date;

public class Product extends Entity {

	public Product() { }

	public Product(int localeId, Date createdAt, int currentRevisionNumber) {
		super(localeId, createdAt, currentRevisionNumber);
	}

	

	//	public boolean isNew() {
	//		return (this.id == null);
	//	}


}
