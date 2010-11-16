package org.tagaprice.shared.rpc;

import java.util.ArrayList;

import org.tagaprice.client.widgets.SearchWidget.SearchType;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.exception.DAOException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("rpc/search")
public interface SearchHandler extends RemoteService {
	ArrayList<Entity> search(String text, SearchType searchType) throws IllegalArgumentException, DAOException;
	ArrayList<Entity> search(String text, SearchType searchType, BoundingBox bbox) throws IllegalArgumentException, DAOException;
	ArrayList<Entity> search(String text, ShopData shopData) throws IllegalArgumentException, DAOException;
}
