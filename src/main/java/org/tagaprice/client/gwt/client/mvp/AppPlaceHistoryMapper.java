package org.tagaprice.client.gwt.client.mvp;

import org.tagaprice.client.gwt.client.places.*;

import com.google.gwt.place.shared.*;

/**
 * Some GWT Magic
 * 
 */
@WithTokenizers({ ListProductsPlace.Tokenizer.class,
		EditProductPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
