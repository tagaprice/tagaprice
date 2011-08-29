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
	private String _lat=null;
	private String _lng=null;
	private String _zoom=null;

	public CreateShopPlace() {
	}

	
	public CreateShopPlace(String id, String revision, String redirectId, String title, String brandId, String lat, String lng, String zoom){
		_id=id;
		_rev=revision;
		_redirectId=redirectId;
		_title=title;
		_brandId=brandId;
		_lat=lat;
		_lng=lng;
		_zoom=zoom;
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
	
	

	/**
	 * @return the lat
	 */
	public String getLat() {
		return _lat;
	}


	/**
	 * @return the lng
	 */
	public String getLng() {
		return _lng;
	}


	/**
	 * @return the zoom
	 */
	public String getZoom() {
		return _zoom;
	}



	@Prefix("shop")
	public static class Tokenizer implements PlaceTokenizer<CreateShopPlace>{


		@Override
		public CreateShopPlace getPlace(String token) {
			Log.debug("Tokenizer token " + token);
			TokenCreator.Exploder e = TokenCreator.getExploder(token);
			
			
			return new CreateShopPlace(
					e.getNode("id"), 
					e.getNode("rev"), 
					e.getNode("redirectid"), 
					e.getNode("title"), 
					e.getNode("brandid"), 
					e.getNode("lat"),
					e.getNode("lng"),
					e.getNode("zoom"));
			
		}

		@Override
		public String getToken(CreateShopPlace place) {
			String rc = null;

			if(place.getId()==null && place.getRedirectId()==null){
				Log.debug("Tokenizer create shop");

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("title", place.getTitle());			
				t.addNode("lat", place.getLat());
				t.addNode("lng", place.getLng());
				t.addNode("zoom", place.getZoom());
				
				rc = t.getToken();
			}else if(place.getId()==null && place.getRedirectId()!=null){
				Log.debug("Tokenizer create with redirect: redirid="+place.getRedirectId()+", brand="+place.getBrandId());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("redirectid", ""+place.getRedirectId());
				t.addNode("title", place.getTitle());
				
				if(place.getBrandId()!=null){
					t.addNode("brandid", ""+place.getBrandId());
				}
				
				t.addNode("lat", place.getLat());
				t.addNode("lng", place.getLng());
				t.addNode("zoom", place.getZoom());

				rc = t.getToken();
			}else if(place.getId()!=null){
				Log.debug("Tokenizer show product: id="+place.getId()+", rev="+place.getRevision());

				TokenCreator.Imploder t = TokenCreator.getImploder();
				t.addNode("id", ""+place.getId());

				if (place.getRevision() != null) {
					t.addNode("rev", ""+place.getRevision());
				}


				t.addNode("lat", place.getLat());
				t.addNode("lng", place.getLng());
				t.addNode("zoom", place.getZoom());
				
				rc = t.getToken();
			}
			
			return rc;
		}

	}
}
