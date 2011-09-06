package org.tagaprice.client.features.searchmanagement;

import org.tagaprice.client.generics.TokenCreator;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SearchPlace extends Place {

	public SearchPlace() {
	}
	
	@Prefix("search")
	public static class Tokenizer implements PlaceTokenizer<SearchPlace>{

		@Override
		public SearchPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			
			return new SearchPlace();
		}

		@Override
		public String getToken(SearchPlace place) {
			// TODO Auto-generated method stub
			return "searchBla";
		}
	}
}
