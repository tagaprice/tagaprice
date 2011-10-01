package org.tagaprice.client.features.startmanagement;

import org.tagaprice.client.generics.TokenCreator;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class StartPlace extends Place {

	private String _redirect = null;
	private String _inviteKey = null;
	
	public StartPlace() {
	}
	
	public StartPlace(String redirect, String inviteKey){
		_redirect=redirect;
		_inviteKey=inviteKey;
	}

	

	/**
	 * @return the registered
	 */
	public String getRedirect() {
		return _redirect;
	}
	
	public String getInviteKey(){
		return _inviteKey;
	}



	@Prefix("start")
	public static class Tokenizer implements PlaceTokenizer<StartPlace>{

		@Override
		public StartPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			
			return new StartPlace(e.getNode("redirect"),e.getNode("invitekey"));
		}

		@Override
		public String getToken(StartPlace place) {
			
			if(place.getRedirect()!=null && place.getRedirect().equals("true")){
				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("redirect", place.getRedirect());
				t.addNode("invitekey",place.getInviteKey());
				return t.getToken();
			}else if(place.getInviteKey()!=null){
				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("invitekey",place.getInviteKey());
				return t.getToken();
			}
			
			return null;
		}

	}

}
