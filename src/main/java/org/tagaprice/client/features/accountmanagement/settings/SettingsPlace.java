package org.tagaprice.client.features.accountmanagement.settings;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class SettingsPlace extends Place {

	public SettingsPlace() {}
	
	@Prefix("settings")
	public static class Tokenizer implements PlaceTokenizer<SettingsPlace>{

		@Override
		public SettingsPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			return new SettingsPlace();
		}

		@Override
		public String getToken(SettingsPlace arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}
