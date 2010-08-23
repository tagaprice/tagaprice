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
import org.tagaprice.shared.BoundingBox;
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
		
		if(searchType.equals(SearchType.ALL)){
			getProduct(mockUp);
			getShop(mockUp);
		}else if(searchType.equals(SearchType.SHOP)){
			getShop(mockUp);
		}else if(searchType.equals(SearchType.PRODCUT)){
			getProduct(mockUp);
		}		
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, SearchType searchType, BoundingBox bbox)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();

		if(searchType.equals(SearchType.ALL)){
			getProduct(mockUp);
			getShop(mockUp);
		}else if(searchType.equals(SearchType.SHOP)){
			getShop(mockUp);
		}else if(searchType.equals(SearchType.PRODCUT)){
			getProduct(mockUp);
		}
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String text, ShopData shopData)
			throws IllegalArgumentException {
		// TODO Auto-generated method stub
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		getProduct(mockUp);
		return mockUp;
	}

	
	private void getProduct(ArrayList<Entity> mockUp){
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
	
	private void getShop(ArrayList<Entity> mockUp){
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
}
