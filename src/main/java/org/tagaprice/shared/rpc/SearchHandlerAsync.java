package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ShopData;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchHandlerAsync {

	void search(String sText, SearchType searchType, AsyncCallback<ArrayList<Entity>> callback)
		throws IllegalArgumentException;
	
	void search(String sText, SearchType searchType, BoundingBox bbox, AsyncCallback<ArrayList<Entity>> callback)
	throws IllegalArgumentException;
	
	void search(String sText, ShopData shopData, AsyncCallback<ArrayList<Entity>> callback)
	throws IllegalArgumentException;
}
