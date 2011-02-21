package org.tagaprice.client.gwt.client.mvp;

import org.tagaprice.client.gwt.client.features.accountmanagement.login.LoginPlace;
import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.ListProductsPlace;
import org.tagaprice.client.gwt.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.gwt.client.features.shopmanagement.listShops.ListShopsPlace;

import com.google.gwt.place.shared.*;

/**
 * Some GWT Magic
 * 
 */

@WithTokenizers({
	ListProductsPlace.Tokenizer.class,
	CreateProductPlace.Tokenizer.class,
	LoginPlace.Tokenizer.class,
	CreateShopPlace.Tokenizer.class,
	ListShopsPlace.Tokenizer.class})
	public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
