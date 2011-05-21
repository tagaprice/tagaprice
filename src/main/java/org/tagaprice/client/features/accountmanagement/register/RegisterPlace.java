package org.tagaprice.client.features.accountmanagement.register;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class RegisterPlace extends Place {


	enum RegisterType {REGISTER, THANKS};

	private RegisterType _type = RegisterType.REGISTER;

	public RegisterPlace() {
		_type=RegisterType.REGISTER;
	}

	public RegisterPlace(RegisterType type){
		_type=type;
	}

	public RegisterType getRegisterType(){
		return _type;
	}

	@Prefix("Register")
	public static class Tokenizer implements PlaceTokenizer<RegisterPlace>{

		@Override
		public RegisterPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);

			TokenCreator.Exploder e = TokenCreator.getExploder(token);

			if(e.getRoot()!=null){
				if(e.getRoot().equals(RegisterType.REGISTER.name())){
					return new RegisterPlace(RegisterType.REGISTER);
				}else if(e.getRoot().equals(RegisterType.THANKS.name())){
					return new RegisterPlace(RegisterType.THANKS);
				}
			}

			return null;
		}

		@Override
		public String getToken(RegisterPlace place) {
			if(place.getRegisterType()==RegisterType.REGISTER){
				Log.debug("Tokenizer show "+RegisterType.REGISTER.name());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot(RegisterType.REGISTER.name());
				return t.getToken();
			}else if(place.getRegisterType()==RegisterType.THANKS){
				Log.debug("Tokenizer show "+RegisterType.THANKS.name());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot(RegisterType.THANKS.name());
				return t.getToken();
			}


			return null;
		}

	}
}
