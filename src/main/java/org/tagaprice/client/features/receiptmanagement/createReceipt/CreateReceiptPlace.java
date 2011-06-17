package org.tagaprice.client.features.receiptmanagement.createReceipt;

import org.tagaprice.client.generics.TokenCreator;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.google.gwt.place.shared.Prefix;

public class CreateReceiptPlace extends Place {
	private String _id = null;
	private String _addId = null;
	private String _addType = null;

	public CreateReceiptPlace() {
	}

	public CreateReceiptPlace(String id){
		_id=id;
	}

	public CreateReceiptPlace(String id, String addId, String addType){
		_id=id;
		_addId=addId;
		_addType=addType;
	}

	public String getId(){
		return _id;
	}



	/**
	 * @return the addId
	 */
	public String getAddId() {
		return _addId;
	}

	/**
	 * @return the addType
	 */
	public String getAddType() {
		return _addType;
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
					if(e.getNode("id")!=null && e.getNode("addid")!=null && e.getNode("addtype")!=null){
						return new CreateReceiptPlace(e.getNode("id"), e.getNode("addid"), e.getNode("addtype"));
					}else if(e.getNode("id")!=null){
						return new CreateReceiptPlace(e.getNode("id"));
					}
					return null;
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

				if(place.getAddId()!=null && place.getAddType()!=null){
					t.addNode("addid", place.getAddId());
					t.addNode("addtype", place.getAddType());
				}

				rc = t.getToken();
			}

			return rc;
		}

	}
}
