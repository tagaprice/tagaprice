package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.client.widgets.SearchWidget.SearchType;
import org.tagaprice.shared.entities.Entity;
import org.tagaprice.shared.entities.Shop;
import org.tagaprice.shared.utility.BoundingBox;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SearchHandlerAsync {

	void search(String sText, SearchType searchType, AsyncCallback<ArrayList<Entity>> callback)
		throws IllegalArgumentException;
	
	void search(String sText, SearchType searchType, BoundingBox bbox, AsyncCallback<ArrayList<Entity>> callback)
	throws IllegalArgumentException;
	
	void search(String sText, Shop shopData, AsyncCallback<ArrayList<Entity>> callback)
	throws IllegalArgumentException;
}
