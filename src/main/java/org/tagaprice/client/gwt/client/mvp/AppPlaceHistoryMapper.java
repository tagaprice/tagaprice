package org.tagaprice.client.gwt.client.mvp;

import org.tagaprice.client.gwt.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.gwt.client.features.productmanagement.editProduct.EditProductPlace;
import org.tagaprice.client.gwt.client.features.productmanagement.listProducts.ListProductsPlace;

import com.google.gwt.place.shared.*;

/**
 * Some GWT Magic
 * 
 */
@WithTokenizers({ ListProductsPlace.Tokenizer.class,
	EditProductPlace.Tokenizer.class, CreateProductPlace.Tokenizer.class })
	public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
