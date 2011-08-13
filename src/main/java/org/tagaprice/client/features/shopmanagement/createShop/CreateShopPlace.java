package org.tagaprice.client.features.shopmanagement.createShop;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateShopPlace extends Place {

	private String _id = null;
	private String _rev = null;
	//private boolean _redirect = false;
	private String _brandId = null;
	private String _title = null;
	private String _redirectId=null;

	public CreateShopPlace() {
	}

	/*
	public CreateShopPlace(String id) {
		_id=id;
	}
	*/

	/*
	public CreateShopPlace(String id, String revision) {
		_id=id;
		_rev=revision;
	}

	
	public CreateShopPlace(String redirectId, String brandId, boolean redirect){
		_id=redirectId;
		_brandId = brandId;
		_redirect=redirect;
	}
	*/
	
	public CreateShopPlace(String id, String revision, String redirectId, String title, String brandId){
		_id=id;
		_rev=revision;
		_redirectId=redirectId;
		_title=title;
		_brandId=brandId;
	}

	public String getRevision() {
		return _rev;
	}

	public String getId() {
		return _id;
	}


	public String getBrandId(){
		return _brandId;
	}

	public String getRedirectId(){
		return _redirectId;
	}
	
	public String getTitle(){
		return _title;
	}

	@Prefix("shop")
	public static class Tokenizer implements PlaceTokenizer<CreateShopPlace>{


		@Override
		public CreateShopPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			
			
			return new CreateShopPlace(e.getNode("id"), e.getNode("rev"), e.getNode("redirectid"), e.getNode("title"), e.getNode("brandid"));
			
			/*
			if(e.getRoot()!=null){
				if(e.getRoot().equals("show")){
					if(e.getNode("id")!=null && e.getNode("rev")!=null){
						return new CreateShopPlace(e.getNode("id"), e.getNode("rev"));
					}else if(e.getNode("id")!=null){
						return new CreateShopPlace(e.getNode("id"));
					}
					return null;
				}else if(e.getRoot().equals("create")){
					if (e.getNode("redirect")!=null && e.getNode("brand")!=null){
						return new CreateShopPlace(e.getNode("redirect"), e.getNode("brand"), true);
					}else if(e.getNode("redirect")!=null && e.getNode("brand")==null){
						return new CreateShopPlace(e.getNode("redirect"), null, true);
					}else{
						return new CreateShopPlace();
					}
				}
			}

			return null;
			*/
		}

		@Override
		public String getToken(CreateShopPlace place) {
			String rc = null;

			if(place.getId()==null && place.getRedirectId()==null){
				Log.debug("Tokenizer create shop");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("title", place.getTitle());
				rc = t.getToken();
			}else if(place.getId()==null && place.getRedirectId()!=null){
				Log.debug("Tokenizer create with redirect: redirid="+place.getRedirectId()+", brand="+place.getBrandId());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("redirectid", ""+place.getRedirectId());
				t.addNode("title", place.getTitle());
				
				if(place.getBrandId()!=null){
					t.addNode("brandid", ""+place.getBrandId());
				}

				rc = t.getToken();
			}else if(place.getId()!=null){
				Log.debug("Tokenizer show product: id="+place.getId()+", rev="+place.getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("id", ""+place.getId());

				if (place.getRevision() != null) {
					t.addNode("rev", ""+place.getRevision());
				}

				rc = t.getToken();
			}
			
			/*
			if(place.getId()==null){
				Log.debug("Tokenizer create shop");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("create");
				rc = t.getToken();
			}else if (place.getId()!=null && place.isRedirect()){
				Log.debug("Tokenizer create with redirect: id="+place.getId()+", redir="+place.isRedirect()+", brand="+place.getBrandId());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("create");
				t.addNode("redirectid", ""+place.getId());

				if(place.getBrandId()!=null){
					t.addNode("brandid", ""+place.getBrandId());
				}

				rc = t.getToken();
			}
			else { // if(place.getRevisionId().getId()!=null)
				Log.debug("Tokenizer show product: id="+place.getId()+", rev="+place.getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.setRoot("show");
				t.addNode("id", ""+place.getId());

				if (place.getRevision() != null) {
					t.addNode("rev", ""+place.getRevision());
				}

				rc = t.getToken();
			}
			*/
			return rc;
		}

	}
}
