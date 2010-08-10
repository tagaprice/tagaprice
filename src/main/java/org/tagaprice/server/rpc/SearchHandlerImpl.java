package org.tagaprice.server.rpc;

import java.util.ArrayList;

import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.rpc.SearchHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SearchHandlerImpl extends RemoteServiceServlet implements SearchHandler{

	@Override
	public ArrayList<Entity> search(String text)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, BoundingBox bbox)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, ShopData shopData)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		return mockUp;
	}

}
