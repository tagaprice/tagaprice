package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.client.SearchWidget2.SearchType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ShopData;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/search")
public interface SearchHandler extends RemoteService {
	ArrayList<Entity> search(String text, SearchType searchType) throws IllegalArgumentException;
	ArrayList<Entity> search(String text, SearchType searchType, BoundingBox bbox) throws IllegalArgumentException;
	ArrayList<Entity> search(String text, ShopData shopData) throws IllegalArgumentException;
}
