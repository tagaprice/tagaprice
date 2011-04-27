package org.tagaprice.client.features.productmanagement.listProducts;

import org.tagaprice.client.generics.TokenCreator;
import org.tagaprice.shared.logging.*;

import com.google.gwt.place.shared.*;

public class ListProductsPlace extends Place {
	private static final MyLogger logger = LoggerFactory
	.getLogger(ListProductsPlace.class);

	private String categoryfilter;

	public ListProductsPlace() {
		ListProductsPlace.logger.log("Empty ListProductPlace created");
	}


	public String getCategoryfilter() {
		return this.categoryfilter;
	}

	@Prefix("ListProducts")
	public static class Tokenizer implements PlaceTokenizer<ListProductsPlace> {

		@Override
		public ListProductsPlace getPlace(String token) {
			ListProductsPlace.logger.log("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			if(e.getRoot()!=null){
				if(e.getRoot().equals("show")){
					ListProductsPlace.logger.log("return ListProductPlace");
					return new ListProductsPlace();
				}
				return null;

			}
			ListProductsPlace.logger.log("return null");
			return null;

		}

		@Override
		public String getToken(ListProductsPlace place) {
			String rc = null;
			TokenCreator.Imploder t = TokenCreator.getImploder();
			t.setRoot("show");
			rc=t.getToken();
			return rc;
		}

	}

}
