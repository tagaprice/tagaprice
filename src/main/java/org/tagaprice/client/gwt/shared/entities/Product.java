package org.tagaprice.client.gwt.shared.entities;
/**
 * 
 * @author Helga Weik (kaltra)
 * The interface Product has methods wich are implemented in the class ProductImpl.java
 *
 */

public interface Product {

	public void setId(int it);

	public int getId();

	public String getName();

	public void setName(String name);

	public void setPrice(int price);

	public int getPrice();

	public String getDescription();

	public void setDescription(String description);

	public void setCategory(String category);

	public String getCategory();

	public void copyFields(Product p);

}
