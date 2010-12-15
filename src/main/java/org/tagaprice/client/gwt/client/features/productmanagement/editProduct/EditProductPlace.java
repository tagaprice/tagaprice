package org.tagaprice.client.gwt.client.features.productmanagement.editProduct;

import org.tagaprice.client.gwt.shared.logging.*;

import com.google.gwt.place.shared.*;

public class EditProductPlace extends Place {
	private static final MyLogger logger = LoggerFactory
			.getLogger(EditProductPlace.class);
	private boolean newProduct;
	private int id;

	public EditProductPlace(int id) {
		logger.log("EditProductPlace created");
		this.newProduct = false;
		this.id = id;
	}

	public EditProductPlace() {
		logger.log("Empty EditProductPlace created");
		this.newProduct = true;
	}

	public boolean isNewProduct() {
		return this.newProduct;
	}

	public int getProductId() {
		return this.id;
	}

	/**
	 * Token for EditProductPlace are either the productId or the String
	 * "create"
	 * 
	 * @author Martin
	 * 
	 */
	public static class Tokenizer implements PlaceTokenizer<EditProductPlace> {

		@Override
		public EditProductPlace getPlace(String token) {
			logger.log("Tokenizer token" + token);
			// token = id
			// activity mit id...
			if (token.equals("create")) {
				return new EditProductPlace();
			}
			try {
				int id = Integer.valueOf(token);
				return new EditProductPlace(id);
			} catch (NumberFormatException e) {
				return null;
			}
		}

		@Override
		public String getToken(EditProductPlace place) {
			if (place.newProduct) {
				logger.log("Tokenizer new product");
				return "create";
			} else {
				logger.log("Tokenizer edit product with id " + place.id);
				return String.valueOf(place.id);
			}
		}

	}

}
