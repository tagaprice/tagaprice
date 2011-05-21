package org.tagaprice.client.features.shopmanagement.createShop;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateShopPlace extends Place {

	private String _id = null;
	private String _rev = null;

	public CreateShopPlace() {
	}

	public CreateShopPlace(String id) {
		_id=id;
	}

	public CreateShopPlace(String id, String revision) {
		_id=id;
		_rev=revision;
	}

	public String getRevision() {
		return _rev;
	}

	public String getId() {
		return _id;
	}


	@Prefix("CreateShop")
	public static class Tokenizer implements PlaceTokenizer<CreateShopPlace>{


		@Override
		public CreateShopPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			if(e.getRoot()!=null){
				if(e.getRoot().equals("show")){
					if(e.getNode("id")!=null && e.getNode("rev")!=null){
						return new CreateShopPlace(e.getNode("id"), e.getNode("rev"));
					}else if(e.getNode("id")!=null){
						return new CreateShopPlace(e.getNode("id"));
					}
					return null;
				}else if(e.getRoot().equals("create")){
					return new CreateShopPlace();
				}
			}

			return null;
		}

		@Override
		public String getToken(CreateShopPlace place) {
			String rc = null;

			if(place.getId()==null){
				Log.debug("Tokenizer create product");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("create");
				rc = t.getToken();
			} else { // if(place.getRevisionId().getId()!=null)
				Log.debug("Tokenizer show product: id="+place.getId()+", rev="+place.getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("show");
				t.addNode("id", ""+place.getId());

				if (place.getRevision() != null) {
					t.addNode("rev", ""+place.getRevision());
				}

				rc = t.getToken();
			}
			return rc;
		}

	}
}
