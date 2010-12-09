package org.tagaprice.client.gwt.client.mvp;

import org.tagaprice.client.gwt.client.places.*;

import com.google.gwt.place.shared.*;

/**
 * Some GWT Magic
 * 
 */
@WithTokenizers({ ProductListPlace.Tokenizer.class,
		EditProductPlace.Tokenizer.class })
public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
