package org.tagaprice.client.features.productmanagement.listProducts;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.*;

public class ListProductsPlace extends Place {


	private String categoryfilter;

	public ListProductsPlace() {
		Log.debug("Empty ListProductPlace created");
	}


	public String getCategoryfilter() {
		return this.categoryfilter;
	}

	@Prefix("ListProducts")
	public static class Tokenizer implements PlaceTokenizer<ListProductsPlace> {

		@Override
		public ListProductsPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			if(e.getRoot()!=null){
				if(e.getRoot().equals("show")){
					Log.debug("return ListProductPlace");
					return new ListProductsPlace();
				}
				return null;

			}
			Log.debug("No token");
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
