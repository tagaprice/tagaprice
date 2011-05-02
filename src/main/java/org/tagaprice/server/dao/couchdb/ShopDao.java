package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ShopDao extends DaoClass<Shop> implements IShopDao {
	public ShopDao(CouchDbDaoFactory daoFactory) {
		super(Shop.class, "shop", daoFactory._getEntityDao());
	}
	
	@Override
	public List<Shop> list() throws DaoException {
		ViewResult<?> result = m_db.queryView("shop/all", Shop.class, null, null);
		List<Shop> rc = new ArrayList<Shop>();
		
		System.out.println("CatList:");
		for (ValueRow<?> row: result.getRows()) {
			Shop shop = get(row.getId());
			rc.add(shop);
		}
		
		return rc;
	}

}
