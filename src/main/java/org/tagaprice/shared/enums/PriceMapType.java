package org.tagaprice.shared.enums;

import org.tagaprice.shared.ISerializable;

public enum PriceMapType implements ISerializable {

	/**
	 * PRODUCT (id: must be a Product_id) displays a map with shops in which
	 * you can find this product plus a price table.
	 */
	PRODUCT,

	/**
	 * PRODUCTGROUP (id: must be a product_group_id (e.g. Milk id)) Shows a
	 * map with shops in which you can find this product-types plus a price
	 * table.
	 */
	CATEGORY,

	/**
	 * SHOP (id: must be a shop_id) Shows a grid with all products you can
	 * find in a shop plus a price table.
	 */
	SHOP,

	/**
	 * SHOPGROUP (id: must be a shop_group_id (e.g. Billa id)) Shows a map with all
	 * shops of shop_group. (Maybe this should be changed to Brand-Group)
	 */
	BRANDING; 

	public String getSerializeName() {
		return "priceMapType";
	}
}