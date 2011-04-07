package org.tagaprice.client.features.productmanagement.createProduct;

import org.tagaprice.client.generics.TokenCreator;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateProductPlace extends Place {
	private static final MyLogger logger = LoggerFactory.getLogger(CreateProductPlace.class);

	private RevisionId _revisonId;

	public CreateProductPlace() {
		_revisonId=new RevisionId();
	}

	public CreateProductPlace(String id){
		_revisonId=new RevisionId(id);
	}

	public CreateProductPlace(String id, String revision){
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
						CreateProductPlace.logger.log("return ProductPlace with it and Revision");
						return new CreateProductPlace(e.getNode("id"), e.getNode("rev"));
					}else if(e.getNode("id")!=null){
						CreateProductPlace.logger.log("return ProductPlace only with id");
						return new CreateProductPlace(e.getNode("id"));
					}
					return null;
				}else if(e.getRoot().equals("create")){
					return new CreateProductPlace();
				}
			}
			CreateProductPlace.logger.log("return null");
			return null;
		}

		@Override
		public String getToken(CreateProductPlace place) {
			String rc = null;
			
			if(place.getRevisionId().getId()==null){
				CreateProductPlace.logger.log("Tokenizer create product");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("create");
				rc = t.getToken();
			} else { //if(place.getRevisionId().getId()!=null)
				CreateProductPlace.logger.log("Tokenizer show product: id="+place.getRevisionId().getId()+", rev="+place.getRevisionId().getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("show");
				t.addNode("id", ""+place.getRevisionId().getId());

				if (place.getRevisionId().getRevision()!=null){
					t.addNode("rev", ""+place.getRevisionId().getRevision());
				}

				rc = t.getToken();
			}
			return rc;
		}

	}
}