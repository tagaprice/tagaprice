package org.tagaprice.client.gwt.shared.entities;

import java.io.Serializable;

/**
 * This Class represents a "Skeleton" of a product with reduced fields for quicker loading.
 * @author Helga Weik (kaltra)
 *
 */
public class ProductCoreImpl implements ProductCore, Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 8964961022397152178L;
	private int id;
	private String name;

	public ProductCoreImpl() {
	}
	/**
	 * The constructor generates a new Object ProductCoreImpl with the parameters below
	 * @param id
	 * @param name
	 */
	public ProductCoreImpl(int id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	/**
	 * Returns the id
	 */
	@Override
	public int getId() {
		return this.id;
	}
	/**
	 * Returns the name
	 */
	@Override
	public String getName() {
		return this.name;
	}

}
