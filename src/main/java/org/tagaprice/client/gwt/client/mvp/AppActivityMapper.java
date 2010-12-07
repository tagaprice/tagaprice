package org.tagaprice.client.gwt.client.mvp;

import org.tagaprice.client.gwt.client.ClientFactory;
import org.tagaprice.client.gwt.client.activities.*;
import org.tagaprice.client.gwt.client.places.*;

import com.google.gwt.activity.shared.*;
import com.google.gwt.place.shared.Place;

/**
 * Maps Places to Activities.
 * 
 */
public class AppActivityMapper implements ActivityMapper {

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		// TODO make this gin
		if (place instanceof ProductListPlace) {
			return new ListProductsActivity((ProductListPlace) place,
					this.clientFactory);
		} else if (place instanceof EditProductPlace) {
			return new EditProductActivity((EditProductPlace) place,
					this.clientFactory);
		}
		return null;
	}

}
