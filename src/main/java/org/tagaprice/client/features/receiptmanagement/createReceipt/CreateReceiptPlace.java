package org.tagaprice.client.features.receiptmanagement.createReceipt;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateReceiptPlace extends Place {
	private String _id = null;

	public CreateReceiptPlace() {
	}

	public CreateReceiptPlace(String id){
		_id=id;
	}

	public String getId(){
		return _id;
	}

	@Prefix("CreateReceipt")
	public static class Tokenizer implements PlaceTokenizer<CreateReceiptPlace>{

		@Override
		public CreateReceiptPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);

			if(e.getRoot()!=null){
				if(e.getRoot().equals("create")){
					return new CreateReceiptPlace();
				}else if(e.getRoot().equals("show")){
					if(e.getNode("id")!=null){
						return new CreateReceiptPlace(e.getNode("id"));
					}
				}
				return null;
			}

			return null;
		}

		@Override
		public String getToken(CreateReceiptPlace place) {
			String rc = null;

			if (place.getId() == null) {
				Log.debug("Tokenizer create receipt");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("create");
				rc = t.getToken();
			} else { // if(place.getId()!=null)
				Log.debug("Tokenizer show receipt: id="+place.getId());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("show");
				t.addNode("id", ""+place.getId());

				rc = t.getToken();
			}

			return rc;
		}

	}
}
