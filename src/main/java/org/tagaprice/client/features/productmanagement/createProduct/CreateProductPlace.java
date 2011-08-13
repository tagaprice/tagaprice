package org.tagaprice.client.features.productmanagement.createProduct;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateProductPlace extends Place {


	private String _id = null;
	private String _rev = null;
	private String _title = null;
	private String _redirectId=null;
	private boolean _redirect = false;

	public CreateProductPlace() {
	}

	/*
	public CreateProductPlace(String id){
		_id=id;
	}

	public CreateProductPlace(String id, String revision){
		_id=id;
		_rev=revision;
	}

	public CreateProductPlace(String redirectId, boolean redirect){
		_redirect=redirect;
		_id=redirectId;
	}
	*/
	
	public CreateProductPlace(String id, String revision, String redirectId, String title){
		_id=id;
		_rev=revision;
		_redirectId=redirectId;
		_title=title;
	}

	public String getRevision() {
		return _rev;
	}

	public String getId() {
		return _id;
	}

	/*
	public boolean isRedirect(){
		return _redirect;
	}
	*/
	
	public String getRedirectId(){
		return _redirectId;
	}
	
	public String getTitle(){
		return _title;
	}

	@Prefix("product")
	public static class Tokenizer implements PlaceTokenizer<CreateProductPlace>{

		@Override
		public CreateProductPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);

			
			return new CreateProductPlace(e.getNode("id"),e.getNode("rev"),e.getNode("redirectid"),e.getNode("title"));
			
			/*
			if(e.getNode("id")!=null && e.getNode("rev")!=null){
				return new CreateProductPlace(e.getNode("id"),e.getNode("rev"),null,null);
			}else if(e.getNode("id")!=null){
				return new CreateProductPlace(e.getNode("id"),null,null,null);
			}else
			
			
			
			
			
			if(e.getRoot()!=null){
				if(e.getRoot().equals("show")){
					if(e.getNode("id")!=null && e.getNode("rev")!=null){
						Log.debug("return ProductPlace with it and Revision");
						return new CreateProductPlace(e.getNode("id"), e.getNode("rev"));
					}else if(e.getNode("id")!=null){
						Log.debug("return ProductPlace only with id");
						return new CreateProductPlace(e.getNode("id"));
					}
					return null;
				}else if(e.getRoot().equals("create")){
					if(e.getNode("redirect")!=null){
						return new CreateProductPlace(e.getNode("redirect"), true);
					}else
						return new CreateProductPlace();
				}
			}
			Log.debug("No token");
			return null;
			*/
		}

		@Override
		public String getToken(CreateProductPlace place) {
			String rc = null;

			if(place.getId()==null && place.getRedirectId()==null){
				Log.debug("Tokenizer create product");
				TokenCreator.Imploder t = TokenCreator.getImploder();
				//t.setRoot("create");
				t.addNode("title", place.getTitle());
				rc = t.getToken();
			}else if(place.getId()==null && place.getRedirectId()!=null){
				Log.debug("Tokenizer create with redirect: redirectid="+place.getRedirectId());
				TokenCreator.Imploder t = TokenCreator.getImploder();
				//t.setRoot("create");
				t.addNode("title", place.getTitle());
				t.addNode("redirectid", ""+place.getRedirectId());
				rc = t.getToken();
			}else if(place.getId()!=null){
				Log.debug("Tokenizer show product: id="+place.getId()+", rev="+place.getRevision());
				TokenCreator.Imploder t = TokenCreator.getImploder();
				//t.setRoot("show");
				t.addNode("id", ""+place.getId());
				if (place.getRevision()!=null){
					t.addNode("rev", ""+place.getRevision());
				}
				rc = t.getToken();
			}
			
			
			/*
			if(place.getId()==null){
				Log.debug("Tokenizer create product");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				//t.setRoot("create");
				
				t.addNode("title", place.getTitle());
				
				
				rc = t.getToken();
			} else if(place.getId()!=null && place.isRedirect()){
				Log.debug("Tokenizer create with redirect: id="+place.getId()+", redir="+place.isRedirect());
				TokenCreator.Imploder t = TokenCreator.getImploder();
				//t.setRoot("create");
				t.addNode("title", place.getTitle());
				t.addNode("redirect", ""+place.getId());
				rc = t.getToken();
			}
			else { //if(place.getRevisionId().getId()!=null)
				Log.debug("Tokenizer show product: id="+place.getId()+", rev="+place.getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				//t.setRoot("show");
				t.addNode("id", ""+place.getId());

				if (place.getRevision()!=null){
					t.addNode("rev", ""+place.getRevision());
				}

				rc = t.getToken();
			}
			*/
			return rc;
		}

	}
}
