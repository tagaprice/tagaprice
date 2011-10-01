package org.tagaprice.client.features.accountmanagement.inviteFriends;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class InviteFriendsPlace extends Place {

	public InviteFriendsPlace() {
		// TODO Auto-generated constructor stub
	}
	
	@Prefix("invitefriends")
	public static class Tokenizer implements PlaceTokenizer<InviteFriendsPlace>{

		@Override
		public InviteFriendsPlace getPlace(String arg0) {
			return new InviteFriendsPlace();
		}

		@Override
		public String getToken(InviteFriendsPlace arg0) {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
	
}
