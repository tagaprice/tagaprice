package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ShopDAO extends DAOClass<Shop> implements IShopDAO {
	public ShopDAO(String dbPrefix) {
		super(dbPrefix, Shop.class, "shop");
	}
	
	@Override
	public List<Shop> list() throws DaoException {
		ViewResult<?> result = m_db.listDocuments(null, null);
		List<Shop> rc = new ArrayList<Shop>();
		
		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			Shop shop = get(row.getId());
			rc.add(shop);
		}
		
		return rc;
	}

}
