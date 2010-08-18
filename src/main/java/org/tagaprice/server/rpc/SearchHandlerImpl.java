package org.tagaprice.server.rpc;

import java.util.ArrayList;

import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.rpc.SearchHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SearchHandlerImpl extends RemoteServiceServlet implements SearchHandler{

	@Override
	public ArrayList<Entity> search(String text, SearchType searchType)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		
		if(searchType.equals(SearchType.ALL)){
			mockUp.add(new ProductData(9L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
			mockUp.add(new ProductData(152L, 3, "test essen"+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_"+Math.random(), 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", "Austria")));
			mockUp.add(new ShopData(12, 3, "Amazon.de "+Math.random(), 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn "+Math.random(), 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}else if(searchType.equals(SearchType.PRODCUT)){
			mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
			mockUp.add(new ProductData(152L, 3, "test essen"+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
		}else if(searchType.equals(SearchType.SHOP)){
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_"+Math.random(), 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", "Austria")));
			mockUp.add(new ShopData(12, 3, "Amazon.de "+Math.random(), 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn "+Math.random(), 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}		
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, SearchType searchType, BoundingBox bbox)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		if(searchType.equals(SearchType.ALL)){
			mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
			mockUp.add(new ProductData(152L, 3, "test essen"+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_"+Math.random(), 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", "Austria")));
			mockUp.add(new ShopData(12, 3, "Amazon.de "+Math.random(), 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn "+Math.random(), 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}else if(searchType.equals(SearchType.PRODCUT)){
			mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
			mockUp.add(new ProductData(152L, 3, "test essen"+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
		}else if(searchType.equals(SearchType.SHOP)){
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_"+Math.random(), 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", "Austria")));
			mockUp.add(new ShopData(12, 3, "Amazon.de "+Math.random(), 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn "+Math.random(), 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}	
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, ShopData shopData)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
		mockUp.add(new ProductData(152L, 3, "test essen"+Math.random(), 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
		return mockUp;
	}

}
