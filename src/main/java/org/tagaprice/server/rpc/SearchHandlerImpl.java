package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.tagaprice.client.widgets.SearchWidget.SearchType;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.server.dao.ShopDAO;
import org.tagaprice.shared.BoundingBox;
import org.tagaprice.shared.data.Entity;
import org.tagaprice.shared.data.Price;
import org.tagaprice.shared.data.Product;
import org.tagaprice.shared.data.Quantity;
import org.tagaprice.shared.data.ShopData;
import org.tagaprice.shared.data.Unit;
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
			pDao = new ProductDAO(db);
			sDao = new ShopDAO(db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}
	
	@Override
	public ArrayList<Entity> search(String search, SearchType searchType)
			throws IllegalArgumentException {
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		
		if(searchType.equals(SearchType.ALL)){
			getProduct(search, mockUp);
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.SHOP)){
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.PRODCUT)){
			getProduct(search, mockUp);
		}		
		
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String search, SearchType searchType, BoundingBox bbox)
			throws IllegalArgumentException {
		ArrayList<Entity> mockUp = new ArrayList<Entity>();

		if(searchType.equals(SearchType.ALL)){
			getProduct(search, mockUp);
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.SHOP)){
			getShop(search, mockUp);
		}else if(searchType.equals(SearchType.PRODCUT)){
			getProduct(search, mockUp);
		}
		return mockUp;
	}

	@Override
	public ArrayList<Entity> search(String search, ShopData shopData)
			throws IllegalArgumentException {
		ArrayList<Entity> mockUp = new ArrayList<Entity>();
		getProduct(search, mockUp);
		return mockUp;
	}

	
	private void getProduct(String search, ArrayList<Entity> mockUp){
		String sql = "SELECT * FROM product pr " +
				"INNER JOIN entity en " +
				"ON en.ent_id = pr.prod_id " +
				"INNER JOIN entityrevision er " +
				"ON er.ent_id=pr.prod_id AND er.rev=current_revision " +
				"WHERE LOWER(er.title) LIKE ?";
		
		
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, "%"+search.toLowerCase()+"%");
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()){
				try {
					Product sp = new Product(res.getLong("ent_id"));
					pDao.get(sp);
					sp.setAvgPrice(new Price(15, 4, 1, "€", 1));
					sp.setImageSrc("logo.png");
					sp.setQuantity(new Quantity(125, new Unit(23, 2, "g", 1, null, 0)));
					sp.setTypeId(20l);
					mockUp.add(sp);
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void getShop(String search, ArrayList<Entity> mockUp){
		String sql = "SELECT * FROM shop pr " +
				"INNER JOIN entity en " +
				"ON en.ent_id = pr.shop_id " +
				"INNER JOIN entityrevision er " +
				"ON er.ent_id=pr.shop_id AND er.rev=current_revision " +
				"WHERE LOWER(er.title) LIKE ?";
		
		
		
		try {
			PreparedStatement pstmt = db.prepareStatement(sql);
			pstmt.setString(1, "%"+search.toLowerCase()+"%");
			ResultSet res = pstmt.executeQuery();
			
			while(res.next()){
				try {
					ShopData sd = new ShopData(res.getLong("ent_id"));
					sDao.get(sd);
					mockUp.add(sd);
				} catch (NotFoundException e) {
					e.printStackTrace();
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
