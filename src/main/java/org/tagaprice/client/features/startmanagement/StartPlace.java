package org.tagaprice.client.features.startmanagement;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class StartPlace extends Place {

	public StartPlace() {
	}


	@Prefix("Start")
	public static class Tokenizer implements PlaceTokenizer<StartPlace>{

		@Override
		public StartPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			return new StartPlace();
		}

		@Override
		public String getToken(StartPlace place) {
			// TODO Auto-generated method stub
			return null;
		}

	}

}
