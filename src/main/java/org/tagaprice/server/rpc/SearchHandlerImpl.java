package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tagaprice.client.SearchWidget.SearchType;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.server.dao.ShopDAO;
import org.tagaprice.shared.Address;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.Country;
import org.tagaprice.shared.Entity;
import org.tagaprice.shared.Price;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.Quantity;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.Unit;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.rpc.SearchHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class SearchHandlerImpl extends RemoteServiceServlet implements SearchHandler{
	ProductDAO pDao;
	ShopDAO sDao;
	DBConnection db;
	
	public SearchHandlerImpl() {
		try {
			db = new DBConnection();
			pDao = ProductDAO.getInstance(db);
			sDao = ShopDAO.getInsance(db);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Entity> search(String text, SearchType searchType)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		
		boolean shop=false;
		boolean product=false;
		if(searchType.equals(SearchType.ALL)){
			shop=true;
			product=true;
		}else if(searchType.equals(SearchType.SHOP)){
			shop=true;
		}else if(searchType.equals(SearchType.PRODCUT)){
			product=true;
		}
		
		if(product){
			//Dirty SQL code
			String sql = "SELECT DISTINCT ent_id, title FROM product p " +
					"INNER JOIN entityrevision er " +
					"ON (p.prod_id=er.ent_id) " +
					"WHERE rev=1 ";
			
			//sql = "SELECT * FROM product";
			
			try {
				PreparedStatement pstmt = db.prepareStatement(sql);
				//pstmt.setString(1, text);
				ResultSet res = pstmt.executeQuery();
				
				while(res.next()){
					try {
						ProductData sp = new ProductData(res.getLong("ent_id"));
						pDao.get(sp);
						sp.setAvgPrice(new Price(15, 4, 1, "€", 1));
						sp.setImageSrc("logo.png");
						sp.setQuantity(new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
						sp.setTypeId(20l);
						mockUp.add(sp);
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(shop){
			//Dirty SQL code
			String sql = "SELECT DISTINCT ent_id, title FROM shop p " +
					"INNER JOIN entityrevision er " +
					"ON (p.shop_id=er.ent_id) " +
					"WHERE rev=1 ";
			
			try {
				PreparedStatement pstmt = db.prepareStatement(sql);
				//pstmt.setString(1, text);
				ResultSet res = pstmt.executeQuery();
				
				while(res.next()){
					try {
						ShopData sd = new ShopData(res.getLong("ent_id"));
						sDao.get(sd);
						//sp.setAvgPrice(new Price(15, 4, 1, "€", 1));
						//sp.setImageSrc("logo.png");
						//sp.setQuantity(new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
						//sp.setTypeId(20l);
						mockUp.add(sd);
					} catch (NotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		/*
		ProductData pd1 = new ProductData(9L, 3, "Mousse au Chocolat ", 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
		pd1.setAvgPrice(new Price(15, 4, 1, "€", 1));
		ProductData pd2 = new ProductData(152L, 3, "test essen", 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
		pd2.setAvgPrice(new Price(15, 4, 1, "€", 1));
		
		if(searchType.equals(SearchType.ALL)){
			mockUp.add(pd1);
			mockUp.add(pd2);
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_", 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", new Country("at", "Austria", "Österreich"))));
			mockUp.add(new ShopData(12, 3, "Amazon.de ", 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn ", 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}else if(searchType.equals(SearchType.PRODCUT)){
			mockUp.add(pd1);
			mockUp.add(pd2);
		}else if(searchType.equals(SearchType.SHOP)){
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_", 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", new Country("at", "Austria", "Österreich"))));
			mockUp.add(new ShopData(12, 3, "Amazon.de ", 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn ", 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}		
		*/
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, SearchType searchType, BoundingBox bbox)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		ProductData pd1 = new ProductData(9L, 3, "Mousse au Chocolat ", 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
		pd1.setAvgPrice(new Price(15, 4, 1, "€", 1));
		ProductData pd2 = new ProductData(152L, 3, "test essen", 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
		pd1.setAvgPrice(new Price(15, 4, 1, "€", 1));
		
		if(searchType.equals(SearchType.ALL)){
			mockUp.add(pd1);
			mockUp.add(pd2);
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_", 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", new Country("at", "Austria", "Österreich"))));
			mockUp.add(new ShopData(12, 3, "Amazon.de ", 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn ", 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}else if(searchType.equals(SearchType.PRODCUT)){
			mockUp.add(pd1);
			mockUp.add(pd2);
		}else if(searchType.equals(SearchType.SHOP)){
			mockUp.add(new ShopData(15, 3, "Billa Flossgasse_", 1, 30l, "logo.png", new Address("Flossgasse 1A", "1020 Wien", new Country("at", "Austria", "Österreich"))));
			mockUp.add(new ShopData(12, 3, "Amazon.de ", 1, 30l, "logo.png", null));
			mockUp.add(new ShopData(15, 3, "Spar Schonbrunn ", 1, 30l, "logo.png", new Address(48.184516, 16.311865)));
		}
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, ShopData shopData)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		mockUp.add(new ProductData(152L, 3, "Mousse au Chocolat ", 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
		mockUp.add(new ProductData(152L, 3, "test essen", 2, 15L, 20L, "logo.png", new Quantity(125, new Unit(23, 2, "g", 1, null, 0))));
		return mockUp;
	}

}
