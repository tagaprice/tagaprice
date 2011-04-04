package org.tagaprice.client.gwt.client.features.productmanagement.listProducts;

import java.util.HashMap;

import org.tagaprice.client.gwt.shared.logging.*;

import com.google.gwt.place.shared.*;

public class ListProductsPlace extends Place {
	private static final MyLogger logger = LoggerFactory
	.getLogger(ListProductsPlace.class);

	private HashMap<String, String> parameters = new HashMap<String, String>();
	private String categoryfilter;

	public ListProductsPlace(String categoryfilter) {
		ListProductsPlace.logger.log("Empty ListProductPlace created");
		this.categoryfilter = categoryfilter;
	}

	public ListProductsPlace(HashMap<String, String> parameters) {
		this.parameters = parameters;
	}

	public String getCategoryfilter() {
		return this.categoryfilter;
	}

	@Prefix("ListProducts")
	public static class Tokenizer implements PlaceTokenizer<ListProductsPlace> {

		@Override
		public ListProductsPlace getPlace(String token) {
			if (token.equals("")) {
				ListProductsPlace.logger.log("return ListProductsPlace");
				return new ListProductsPlace(new HashMap<String, String>());
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
				ListProductsPlace.logger.log("return other ListProductsPlace");
				return new ListProductsPlace(token);
			}
		}

		@Override
		public String getToken(ListProductsPlace place) {
			if (place.parameters.size() == 0) {
				return "";
			} else {
				// TODO fill this with code...
				return "";
			}
		}

	}

}
