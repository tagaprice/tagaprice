package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.List;

import org.jcouchdb.document.ValueRow;
import org.jcouchdb.document.ViewResult;
import org.tagaprice.server.dao.ICategoryDao;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ShopDao extends DaoClass<Shop> implements IShopDao {
	
	private ICategoryDao m_shopCategoryDAO;;
	
	public ShopDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, Shop.class, "shop", daoFactory._getEntityDao());
		m_shopCategoryDAO = daoFactory.getShopCategoryDao();
	}
	
	@Override
	public List<Shop> list() throws DaoException {
		ViewResult<?> result = m_db.queryView("shop/all", Shop.class, null, null);
		List<Shop> rc = new ArrayList<Shop>();
		
		for (ValueRow<?> row: result.getRows()) {
			Shop shop = get(row.getId());
			rc.add(shop);
		}
		
		return rc;
	}

	@Override
	protected void _injectFields(Shop shop) throws DaoException {
		if (shop.getCategoryId() != null) {
			shop.setCategory(m_shopCategoryDAO.get(shop.getCategoryId()));
		}
	}

}
