package org.tagaprice.client.gwt.client.places;

import java.util.HashMap;

import org.tagaprice.client.gwt.shared.logging.*;


import com.google.gwt.place.shared.*;

public class ProductListPlace extends Place {
	private static final MyLogger logger = LoggerFactory
			.getLogger(ProductListPlace.class);

	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String categoryfilter;

	public ProductListPlace(String categoryfilter) {
		logger.log("Empty ListProductPlace created");
		this.categoryfilter = categoryfilter;
	}

	public ProductListPlace(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getCategoryfilter() {
		return this.categoryfilter;
	}

	public static class Tokenizer implements PlaceTokenizer<ProductListPlace> {

		@Override
		public ProductListPlace getPlace(String token) {
			if (token.equals("")) {
				return new ProductListPlace(new HashMap<String, String>());
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

				return new ProductListPlace(token);
			}
		}

		@Override
		public String getToken(ProductListPlace place) {
			if (place.parameters.size() == 0) {
				return "";
			} else {
				// TODO fill this with code...
				return "";
			}
		}

	}

}
