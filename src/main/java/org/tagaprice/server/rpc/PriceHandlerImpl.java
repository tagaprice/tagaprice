/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPrice
 * Filename: PriceHandlerImpl.java
 * Date: 02.06.2010
*/
package org.tagaprice.server.rpc;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.client.widgets.PriceMapWidget.PriceMapType;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.ProductDAO;
import org.tagaprice.server.dao.ShopDAO;
import org.tagaprice.shared.entities.Price;
import org.tagaprice.shared.entities.PriceData;
import org.tagaprice.shared.entities.Product;
import org.tagaprice.shared.entities.Shop;
import org.tagaprice.shared.exception.NotFoundException;
import org.tagaprice.shared.rpc.PriceHandler;
import org.tagaprice.shared.utility.BoundingBox;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PriceHandlerImpl extends RemoteServiceServlet implements PriceHandler {

	private DBConnection db;
	private ShopDAO shopDAO;
	private ProductDAO productDAO;
	
	public PriceHandlerImpl() {
		try {
			db = new DBConnection();
			shopDAO = new ShopDAO(db);
			productDAO = new ProductDAO(db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	@Override
	public ArrayList<PriceData> get(long id, BoundingBox bbox, PriceMapType type)
			throws IllegalArgumentException {
		ArrayList<PriceData> list = new ArrayList<PriceData>();
		
		PreparedStatement pstmt; 
		try {
		
			if(type.equals(PriceMapType.PRODUCT)){
				//return Last price plus location
				
				
					
					String sql ="SELECT pid, sid, lat, lng, price, re.created_at " +
							"FROM receiptentry ree INNER JOIN receipt re " +
							"ON re.rid=ree.rid " +
							"INNER JOIN entity en " +
							"ON en.ent_id=re.sid " +
							"INNER JOIN shoprevision sr " +
							"ON sr.shop_id=en.ent_id AND sr.rev=en.current_revision " +
							"WHERE ree.pid = ? AND re.draft=false " +
							"AND (lat BETWEEN ? AND ?) " +
							"AND (lng BETWEEN ? AND ?)";
					
					
					pstmt = db.prepareStatement(sql);
					pstmt.setLong(1, id); //id
					
					pstmt.setDouble(2, bbox.getX1()); //lat left
					pstmt.setDouble(3, bbox.getX2()); //lat right
					
					pstmt.setDouble(4,bbox.getY1()); //lng top
					pstmt.setDouble(5, bbox.getY2()); //lng down
					
					
					ResultSet res = pstmt.executeQuery();
					//ProductData tempProduct = new ProductData(res.getLong("pid"));
					Product tempProduct=null;
					while(res.next()){
						
						if(tempProduct==null) tempProduct = new Product(res.getLong("pid"));
						
						Shop tempShop = new Shop(res.getLong("sid"));
						productDAO.get(tempProduct);
						shopDAO.get(tempShop);
						
						
						list.add(new PriceData(
								tempProduct, 
								tempShop,
								new Price(res.getInt("price"), 23, 8, "€", 1), 
								new Date(res.getDate("created_at").getTime())));
					}
									
			}
			
			if(type.equals(PriceMapType.SHOP)){
				String sql = "SELECT * FROM receiptentry ree " +
				"INNER JOIN receipt re " +
				"ON re.rid=ree.rid " +
				"WHERE re.sid = ? AND re.draft=false";
				
				pstmt = db.prepareStatement(sql);
				pstmt.setLong(1, id);
				
				ResultSet res = pstmt.executeQuery();
				
				while(res.next()){
					Product tempProduct = new Product(res.getLong("pid"));
					//ShopData tempShop = new ShopData(res.getLong("sid"));
					productDAO.get(tempProduct);
					//shopDAO.get(tempShop);
					
					
					list.add(new PriceData(
							tempProduct, 
							null,
							new Price(res.getInt("price"), 23, 8, "€", 1), 
							new Date(res.getDate("created_at").getTime())));
				}
				
			}
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return list;
	}

}
