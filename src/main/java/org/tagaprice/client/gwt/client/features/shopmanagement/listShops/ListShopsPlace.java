package org.tagaprice.client.gwt.client.features.shopmanagement.listShops;

import java.util.HashMap;

import org.tagaprice.client.gwt.shared.logging.*;

import com.google.gwt.place.shared.*;

public class ListShopsPlace extends Place {
	private static final MyLogger logger = LoggerFactory
	.getLogger(ListShopsPlace.class);

	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String categoryfilter;

	public ListShopsPlace(String categoryfilter) {
		ListShopsPlace.logger.log("Empty ListShopsPlace created");
		this.categoryfilter = categoryfilter;
	}

	public ListShopsPlace(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getCategoryfilter() {
		return this.categoryfilter;
	}

	@Prefix("listShops")
	public static class Tokenizer implements PlaceTokenizer<ListShopsPlace> {

		@Override
		public ListShopsPlace getPlace(String token) {
			if (token.equals("")) {
				return new ListShopsPlace(new HashMap<String, String>());
			} else {
				String separator = "&";
				String sign = "=";
				String[] values = token.split(separator);
				HashMap<String, String> parameters = new HashMap<String, String>();
				// TODO make it save...
				for (String value : values) {
					String[] pair = value.split(sign);
					parameters.put(pair[0], pair[1]);
				}

				return new ListShopsPlace(token);
			}
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
