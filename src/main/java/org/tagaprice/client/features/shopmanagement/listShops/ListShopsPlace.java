package org.tagaprice.client.features.shopmanagement.listShops;

import java.util.HashMap;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.*;

public class ListShopsPlace extends Place {
	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String categoryfilter;

	public ListShopsPlace(String categoryfilter) {
		Log.debug("Empty ListShopsPlace created");
		this.categoryfilter = categoryfilter;
	}

	public ListShopsPlace(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getCategoryfilter() {
		return this.categoryfilter;
	}

	@Prefix("ListShops")
	public static class Tokenizer implements PlaceTokenizer<ListShopsPlace> {

		@Override
		public ListShopsPlace getPlace(String token) {
			Log.debug("returning new ListShopsPlace");
			return new ListShopsPlace(new HashMap<String, String>());
		}

		@Override
		public String getToken(ListShopsPlace place) {
			if (place.parameters.size() == 0) {
				return "";
			} else {
				// TODO fill this with code...
				return "";
			}
		}

	}

}
