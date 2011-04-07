package org.tagaprice.client.features.shopmanagement.createShop;

import org.tagaprice.client.generics.TokenCreator;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateShopPlace extends Place {
	private static final MyLogger logger = LoggerFactory.getLogger(CreateShopPlace.class);

	private RevisionId _revisonId;

	public CreateShopPlace() {
		_revisonId=new RevisionId();
	}

	public CreateShopPlace(String id) {
		_revisonId=new RevisionId(id);
	}

	public CreateShopPlace(String id, String revision) {
		_revisonId=new RevisionId(id,revision);
	}

	public RevisionId getRevisionId(){
		return _revisonId;
	}

	@Prefix("CreateShop")
	public static class Tokenizer implements PlaceTokenizer<CreateShopPlace>{


		@Override
		public CreateShopPlace getPlace(String token) {
			CreateShopPlace.logger.log("Tokenizer token " + token);
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
			
			if(place.getRevisionId().getId()==null){
				CreateShopPlace.logger.log("Tokenizer create product");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("create");
				rc = t.getToken();
			} else { // if(place.getRevisionId().getId()!=null)
				CreateShopPlace.logger.log("Tokenizer show product: id="+place.getRevisionId().getId()+", rev="+place.getRevisionId().getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("show");
				t.addNode("id", ""+place.getRevisionId().getId());

				if (place.getRevisionId().getRevision() != null) {
					t.addNode("rev", ""+place.getRevisionId().getRevision());
				}

				rc = t.getToken();
			}
			return rc;
		}

	}
}
