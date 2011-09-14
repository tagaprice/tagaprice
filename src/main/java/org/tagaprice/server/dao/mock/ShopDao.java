package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Document;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class ShopDao extends DaoClass<Shop> implements IShopDao {

	HashMap<String, Shop> shopsAllRevisions = new HashMap<String, Shop>();
	HashMap<String, String> newestRev = new HashMap<String, String>();
	Random random = new Random(1654196865);


	@Override
	public List<Shop> list() {
		ArrayList<Shop> rc = new ArrayList<Shop>();

		for (Deque<Shop> deque: m_data.values()) {
			Shop st = deque.peek();
			st._setDocType(Document.Type.SHOP);
			rc.add(deque.peek());
		}

		return rc;
	}

	@Override
	public List<Shop> find(String searchPattern) throws DaoException {
		ArrayList<Shop> rc = new ArrayList<Shop>();
		
		for(Deque<Shop> deque: m_data.values()){
			Shop shop = deque.peek();
			shop._setDocType(Document.Type.SHOP);
			
			if (searchPattern != null
					&& shop.getTitle().toLowerCase().contains((searchPattern.toLowerCase()))) {
				rc.add(shop);
			} else if (searchPattern == null) {
				rc.add(shop);
			}
		}
		
		
		return rc;
	}
	
	@Override
	public List<String> findIDsThatSell(BoundingBox bbox, List<String> packageIDs) throws DaoException {
		List<String> rc = new ArrayList<String>();
		
		for (Shop shop: list()) {
			if (bbox.contains(shop.getAddress().getPos())) {
				rc.add(shop.getId());
			}
		}
		
		return rc;
	}


}
