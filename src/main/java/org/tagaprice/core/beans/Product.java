package org.tagaprice.core.beans;

public class Product extends Entity<Product> {

	public Product() {
		super();
	}

	@Override
	protected Product self() {
		return this;
	}


	//	public boolean isNew() {
	//		return (this.id == null);
	//	}


}
