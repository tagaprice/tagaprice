package org.tagaprice.server.rpc;

import java.util.ArrayList;

import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
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
		
		mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", 20, 80, new Price(139, 23, 1, "€", 1), new Quantity(125, 23, 2, "g", 1),true));
		mockUp.add(new ShopData(15, 3, "Billa Flossgasse_"+Math.random(), 1, "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")));
		mockUp.add(new ShopData(12, 3, "Amazon.de "+Math.random(), 1, "logo.png", 80, 3));
		mockUp.add(new ShopData(15, 3, "Billa Flossgasse "+Math.random(), 1, "logo.png", 80, 50, new Address(48.217883, 16.390475)));
		mockUp.add(new ShopData(15, 3, "Spar Schonbrunn "+Math.random(), 1, "logo.png", 20, 70, new Address(48.184516, 16.311865)));
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, BoundingBox bbox)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", 20, 80, new Price(139, 23, 1, "€", 1), new Quantity(125, 23, 2, "g", 1),true));
		mockUp.add(new ShopData(15, 3, "Billa Flossgasse_"+Math.random(), 1, "logo.png", 80, 50, new Address("Flossgasse 1A", "1020 Wien", "Austria")));
		mockUp.add(new ShopData(12, 3, "Amazon.de "+Math.random(), 1, "logo.png", 80, 3));
		mockUp.add(new ShopData(15, 3, "Billa Flossgasse "+Math.random(), 1, "logo.png", 80, 50, new Address(48.217883, 16.390475)));
		mockUp.add(new ShopData(15, 3, "Spar Schonbrunn "+Math.random(), 1, "logo.png", 20, 70, new Address(48.184516, 16.311865)));
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, ShopData shopData)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat "+Math.random(), 2, 15L, 20L, "logo.png", 20, 80, new Price(139, 23, 1, "€", 1), new Quantity(125, 23, 2, "g", 1),true));
		
		return mockUp;
	}

}
