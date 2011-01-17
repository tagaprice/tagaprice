package org.tagaprice.client.gwt.client.features.productmanagement.createProduct;

import org.tagaprice.client.gwt.client.generics.TokenCreator;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateProductPlace extends Place {
	private static final MyLogger logger = LoggerFactory.getLogger(CreateProductPlace.class);

	private RevisionId _revisonId;

	public CreateProductPlace() {
		_revisonId=new RevisionId();
	}

	public CreateProductPlace(long id){
		_revisonId=new RevisionId(id);
	}

	public CreateProductPlace(long id, long revision){
		_revisonId=new RevisionId(id, revision);
	}

	public RevisionId getRevisionId(){
		return _revisonId;
	}

	@Prefix("CreateProduct")
	public static class Tokenizer implements PlaceTokenizer<CreateProductPlace>{

		@Override
		public CreateProductPlace getPlace(String token) {
			CreateProductPlace.logger.log("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			if(e.getRoot()!=null){
				if(e.getRoot().equals("show")){
					if(e.getNode("id")!=null && e.getNode("rev")!=null){
						return new CreateProductPlace(Long.parseLong(e.getNode("id")), Long.parseLong(e.getNode("rev")));
					}else if(e.getNode("id")!=null){
						return new CreateProductPlace(Long.parseLong(e.getNode("id")));
					}
					return null;
				}else if(e.getRoot().equals("create")){
					return new CreateProductPlace();
				}
			}
			return null;
		}

		@Override
		public String getToken(CreateProductPlace place) {
			if(place.getRevisionId().getId()==null){
				CreateProductPlace.logger.log("Tokenizer create product");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("create");
				return t.getToken();
			}else if(place.getRevisionId().getId()!=null){
				CreateProductPlace.logger.log("Tokenizer show product: id="+place.getRevisionId().getId()+", rev="+place.getRevisionId().getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("show");
				t.addNode("id", ""+place.getRevisionId().getId());

				if(place.getRevisionId().getRevision()!=null){
					t.addNode("rev", ""+place.getRevisionId().getRevision());
				}

				return t.getToken();
			}
			return null;
		}

	}
}
