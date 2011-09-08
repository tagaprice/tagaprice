package org.tagaprice.client.features.categorymanagement;

import org.tagaprice.client.generics.TokenCreator;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CategoryPlace extends Place {

	public CategoryPlace() {
		// TODO Auto-generated constructor stub
	}
	
	@Prefix("category")
	public static class Tokenizer implements PlaceTokenizer<CategoryPlace>{

		@Override
		public CategoryPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			
			return new CategoryPlace();
		}

		@Override
		public String getToken(CategoryPlace place) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
