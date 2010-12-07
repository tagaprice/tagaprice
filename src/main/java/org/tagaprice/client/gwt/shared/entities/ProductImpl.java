package org.tagaprice.client.gwt.shared.entities;

import java.io.Serializable;
/**
 * 
 * @author Helga Weik (kaltra)
 * A Product has properties: id, name, price, description and category
 * The class implements the Interface Product und the Interface Serializable
 */

public class ProductImpl implements Product, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7265959542901935615L;
	private int id;
	private String name;
	private int price;
	private String description;
	private String category;

	public ProductImpl() {
	}
/**
 * The Constructor generates a new Object Product with the parameters below
 * @param id
 * @param name
 * @param price
 * @param description
 * @param category
 */
	public ProductImpl(int id, String name, int price, String description,
			String category) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.category = category;
	}
/**
 * Returns the id of a product
 */
	@Override
	public int getId() {
		return this.id;
	}
/**
 * Sets the id of a product
 */
	@Override
	public void setId(int id) {
		this.id = id;
	}
/**
 * returns the name of a product
*/
	@Override
	public String getName() {
		return this.name;
	}
/**
 * Sets the name of product
 */
	@Override
	public void setName(String name) {
		this.name = name;
	}
/**
 * returns the description of a product
*/
	@Override
	public String getDescription() {
		return this.description;
	}

/**
 * Sets the description of a product
 */
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
/**
 * returns the category of a product
 */
	@Override
	public String getCategory() {
		return this.category;
	}
/**
 * Sets the cotegory of a product for example Icetee is a drink
 */
	@Override
	public void setCategory(String category) {
		this.category = category;
	}
/**
 * ?
 */
	@Override
	public void copyFields(Product p) {
		this.id = p.getId();
		this.name = p.getName();
		this.price = p.getPrice();
		this.description = p.getDescription();
		this.category = p.getCategory();
	}
/**
 * Sets the price for a product
 */
	@Override
	public void setPrice(int price) {
		this.price = price;
	}
/**
 * Returns the price of a product
 */
	@Override
	public int getPrice() {
		return this.price;
	}

}
