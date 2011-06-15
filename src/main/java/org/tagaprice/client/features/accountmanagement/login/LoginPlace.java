package org.tagaprice.client.features.accountmanagement.login;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class LoginPlace extends Place {

	private String _sessionId;

	public LoginPlace() {
		// TODO Auto-generated constructor stub
	}

	public LoginPlace(String sessionId){
		_sessionId=sessionId;
	}

	/**
	 * Returns the sessionID is a user is logged in.
	 * @return the sessionID is a user is logged in.
	 */
	public String getSessionId(){
		return _sessionId;
	}

	@Prefix("LogInOut")
	public static class Tokenizer implements PlaceTokenizer<LoginPlace>{


		@Override
		public LoginPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);

			TokenCreator.Exploder e = TokenCreator.getExploder(token);

			//At the moment the rootToken will always be NULL. so the method will return a new LoginPlace
			if(e.getRoot()!=null){
				if(e.getRoot().equals("login")){
					return new LoginPlace();
				}else if(e.getRoot().equals("logout")){
					//TODO get SessionID on this place
					//return new LoginPlace();
					return new LoginPlace();
				}else{
					return new LoginPlace();
				}
			}

			return null;
		}

		@Override
		public String getToken(LoginPlace place) {
			if(place.getSessionId()==null){
				Log.debug("Tokenizer show login");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("login");
				return t.getToken();
			}else if(place.getSessionId()!=null){
				Log.debug("Tokenizer show logout");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("logout");
				return t.getToken();
			}

			return null;
		}

	}
}
