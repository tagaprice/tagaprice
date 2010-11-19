/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License.
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
 */

/**
 * Project: TagAPriceUI
 * Filename: ProductPreviewContainer.java
 * Date: 14.05.2010
 */
package org.tagaprice.shared.entities;


/**
 * Basically, a {@link Product} is represented by following properties:
 * - A {@link Quantity} describing how this {@link Product} is measured
 * - An average {@link Price} (TODO how is this defined? what timespan is used?)
 * - A {@link Category} - A brand (TODO create a Brand class)
 * - An Image (represented as an URL)
 * 
 * Furthermore, it is has an associated rating in percent (-1 meaning unrated), and a progress meter in percent, showing
 * how complete the collected information about this {@link Product} is.
 */
public class Product extends Entity {
	private static final long serialVersionUID = 1L;

	/** TODO create a Brand class */
	private Long _brandId;
	private Long _categoryId;
	private String _imageSrc;
	/** In percent 0-100 */
	private int _progress = 0;
	/** in percent 0-100, -1 means unrated */
	private int _rating = -1;
	private Price _avgPrice;
	private Quantity _quantity = null;
	private boolean _hasReceipt;

	/**
	 * default constructor (used for serialization)
	 */
	public Product() {
		super();
	}

	/**
	 * Constructor for querying a {@link Product}'s current revision
	 * 
	 * @param id
	 *            {@link Product} ID
	 */
	public Product(Long id) {
		super(id);
	}

	/**
	 * Query a specific {@link Product} revision
	 * 
	 * @param id
	 *            Poduct ID
	 * @param rev
	 *            revision to query
	 */
	public Product(Long id, int rev) {
		super(id, rev);
	}

	/**
	 * Constructor for creating a new {@link Product} in the database
	 * 
	 * @param title
	 *            descriptive {@link Product} title (must not be empty or NULL)
	 * @param localeId
	 *            {@link Product} locale
	 * @param creatorId
	 *            {@link Product}'s creator
	 * @param brandId
	 *            {@link Product} Brand (may be null if not yet set)
	 * @param categoryId
	 *            {@link Product}s {@link Category} (may be null if not yet set)
	 * @param imageSrc
	 *            image file URL (may be null if not yet set)
	 * @param qantity
	 *            {@link Product} quantity (e.g. weight, volume, ...)
	 */
	public Product(String title, int localeId, long creatorId, Long brandId, Long categoryId, String imageSrc,
			Quantity qantity) {
		super(title, localeId, creatorId);
		_brandId = brandId;
		_categoryId = categoryId;
		_imageSrc = imageSrc;
		_quantity = qantity;
	}

	/**
	 * constructor for saving an existing Product
	 * 
	 * @param id
	 *            Product ID
	 * @param rev
	 *            last existing revision (e.g. the revision this version is based on)
	 * @param title
	 *            (new) descriptive Product name
	 * @param creatorId
	 *            revision's creator ID
	 * @param brandId
	 *            (new) Brand ID (might be null)
	 * @param typeId
	 *            (new) ProductType ID (might be null)
	 * @param imageSrc
	 *            (new) image source URL (might be null)
	 * @param qantity
	 *            (new) quantity (e.g. weight)
	 */
	public Product(long id, int rev, String title, long creatorId, Long brandId, Long typeId, String imageSrc,
			Quantity qantity) {
		super(id, rev, title, creatorId);
		_brandId = brandId;
		_categoryId = typeId;
		_imageSrc = imageSrc;
		_quantity = qantity;
	}

	/**
	 * @return the brand ID
	 */
	public Long getBrandId() {
		return _brandId;
	}


	/**
	 * @param brandId
	 *            the brand ID to set
	 */
	public void setBrandId(Long brandId) {
		_brandId = brandId;
	}


	/**
	 * @return the typeId
	 */
	public Long getCategoryId() {
		return _categoryId;
	}


	/**
	 * @param categoryId
	 *            the {@link Category} ID to set
	 */
	public void setCategoryId(Long categoryId) {
		_categoryId = categoryId;
	}

	/**
	 * 
	 * @return the image URL
	 */
	public String getImageSrc() {
		return _imageSrc;
	}

	/**
	 * 
	 * @param imageSrc
	 *            the image URL
	 */
	public void setImageSrc(String imageSrc) {
		_imageSrc = imageSrc;
	}

	/**
	 * 
	 * @return completeness of the collected information about this {@link Product} in percent.
	 *         If aprox. all data about this Product is known, this will return 100.
	 */
	public int getProgress() {
		return _progress;
	}

	/**
	 * 
	 * @param progress
	 *            completeness of the collected information about this product, in percent
	 *            (valid values in the range of 1 - 100)
	 */
	public void setProgress(int progress) {
		_progress = progress;
	}

	/**
	 * 
	 * @return the rating of this product
	 */
	public int getRating() {
		return _rating;
	}

	/**
	 * 
	 * @param rating
	 *            the rating of this product
	 */
	public void setRating(int rating) {
		_rating = rating;
	}

	/**
	 * @return product's average price
	 */
	public Price getAvgPrice() {
		return _avgPrice;
	}

	/**
	 * @param price
	 *            product's average price
	 */
	public void setAvgPrice(Price price) {
		_avgPrice = price;
	}

	/**
	 * 
	 * @return {@link Quantity} of this product
	 */
	public Quantity getQuantity() {
		return _quantity;
	}

	/**
	 * 
	 * @param quantitiy
	 *            {@link Quantity} of this product
	 */
	public void setQuantity(Quantity quantity) {
		_quantity = quantity;
	}

	/**
	 * 
	 * @return true, if a receipt exists for this product
	 */
	public boolean hasReceipt() {
		return _hasReceipt;
	}

	/**
	 * 
	 * @param hasReceipt
	 *            true, if a receipt exists for this product
	 */
	public void setHasReceipt(boolean hasReceipt) {
		_hasReceipt = hasReceipt;
	}

	@Override
	public String getSerializeName() {
		return "product";
	}

	@Override
	public boolean equals(Object o) {
		boolean rc = super.equals(o);

		if (rc && o instanceof Product) {
			Product p = (Product) o;
			if (!Entity._compare(getBrandId(), p.getBrandId()))
				rc = false;
			if (!Entity._compare(getCategoryId(), p.getCategoryId()))
				rc = false;
			if (!Entity._compare(getImageSrc(), p.getImageSrc()))
				rc = false;
			if (getProgress() != p.getProgress())
				rc = false;
			if (getRating() != p.getRating())
				rc = false;
			if (!Entity._compare(getAvgPrice(), p.getAvgPrice()))
				rc = false;
			if (!Entity._compare(getQuantity(), p.getQuantity()))
				rc = false;
			if (hasReceipt() != p.hasReceipt())
				rc = false;
		} else
			rc = false;

		return rc;
	}

	@Override
	public String toString() {
		return "Product {\n" + "brandId: " + getBrandId() + "\ntypeId: " + getCategoryId() + "\nimageSrc: "
		+ getImageSrc() + "\nprogress: " + +getProgress() + "\nrating: " + getRating() + "\navgPrice: "
		+ getAvgPrice() + "\nquantity: " + getQuantity() + "\nhasReceipt: " + hasReceipt() + "\n"
		+ super.toString() + "}\n";
	}

	/**
	 * TODO implement this function
	 */
	@Override
	public <T extends Entity> T newRevision() {
		// TODO Auto-generated method stub
		return null;
	}
}
