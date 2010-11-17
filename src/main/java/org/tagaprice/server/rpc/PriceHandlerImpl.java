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
import java.util.ArrayList;

import org.tagaprice.client.widgets.PriceMapWidget.PriceMapType;
import org.tagaprice.server.DBConnection;
import org.tagaprice.server.dao.postgres.ProductDAO;
import org.tagaprice.server.dao.postgres.ShopDAO;
import org.tagaprice.shared.entities.PriceData;
import org.tagaprice.shared.rpc.PriceHandler;
import org.tagaprice.shared.utility.BoundingBox;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class PriceHandlerImpl extends RemoteServiceServlet implements PriceHandler {

	private DBConnection _db;
	@SuppressWarnings("unused")
	private ShopDAO _shopDAO;
	@SuppressWarnings("unused")
	private ProductDAO _productDAO;
	
	public PriceHandlerImpl() {
		try {
			_db = new DBConnection();
			_shopDAO = new ShopDAO(_db);
			_productDAO = new ProductDAO(_db);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException(e);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		} 
	}
	
	@Override
	public ArrayList<PriceData> get(long id, BoundingBox bbox, PriceMapType priceMapType)
	throws IllegalArgumentException {
		ArrayList<PriceData> list = new ArrayList<PriceData>();

		//TODO IMPLEMENT in DAO
//		PreparedStatement pstmt;
//		String sql;
//		ResultSet res;
//		switch(priceMapType)
//		{
//		case PRODUCT:
//			sql ="SELECT pid, sid, lat, lng, price, re.created_at " +
//			"FROM receiptentry ree INNER JOIN receipt re " +
//			"ON re.rid=ree.rid " +
//			"INNER JOIN entity en " +
//			"ON en.ent_id=re.sid " +
//			"INNER JOIN shoprevision sr " +
//			"ON sr.shop_id=en.ent_id AND sr.rev=en.current_revision " +
//			"WHERE ree.pid = ? AND re.draft=false " +
//			"AND (lat BETWEEN ? AND ?) " +
//			"AND (lng BETWEEN ? AND ?)";
//
//
//			pstmt = db.prepareStatement(sql);
//			pstmt.setLong(1, id); //id
//
//			pstmt.setDouble(2, bbox.getX1()); //lat left
//			pstmt.setDouble(3, bbox.getX2()); //lat right
//
//			pstmt.setDouble(4,bbox.getY1()); //lng top
//			pstmt.setDouble(5, bbox.getY2()); //lng down
//
//
//			res = pstmt.executeQuery();
//			ProductData tempProduct=productDAO.getById(res.getLong("pid")); //TODO this should be in the while loop i think
//			while(res.next()){
//				list.add(new PriceData(
//						tempProduct, 
//						shopDAO.getById(res.getLong("sid")),
//						new Price(res.getInt("price"), 23, 8, "€", 1), 
//						new Date(res.getDate("created_at").getTime())));
//			}
//			break;
//		case SHOP:
//			sql = "SELECT * FROM receiptentry ree " +
//			"INNER JOIN receipt re " +
//			"ON re.rid=ree.rid " +
//			"WHERE re.sid = ? AND re.draft=false";
//
//			pstmt = db.prepareStatement(sql);
//			pstmt.setLong(1, id);
//
//			res = pstmt.executeQuery();
//
//			while(res.next()){
//				list.add(new PriceData(
//						productDAO.getById(res.getLong("pid")),
//						null,
//						new Price(res.getInt("price"), 23, 8, "€", 1), 
//						new Date(res.getDate("created_at").getTime())));
//			}
//			break;
//
//		default:
//			throw new IllegalArgumentException("Unexcepted PriceMapType: "+priceMapType);
//		}
		return list;
	}

}
