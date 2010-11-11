package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.client.widgets.SearchWidget.SearchType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.data.Entity;
import org.tagaprice.shared.data.ShopData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/search")
public interface SearchHandler extends RemoteService {
	ArrayList<Entity> search(String text, SearchType searchType) throws IllegalArgumentException;
	ArrayList<Entity> search(String text, SearchType searchType, BoundingBox bbox) throws IllegalArgumentException;
	ArrayList<Entity> search(String text, ShopData shopData) throws IllegalArgumentException;
}
