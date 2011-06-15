package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.tagaprice.server.dao.IShopDao;
import org.tagaprice.shared.entities.shopmanagement.Shop;

public class ShopDao extends DaoClass<Shop> implements IShopDao {

	HashMap<String, Shop> shopsAllRevisions = new HashMap<String, Shop>();
	HashMap<String, String> newestRev = new HashMap<String, String>();
	Random random = new Random(1654196865);

	@Override
	public List<Shop> find(String query) {
		return list();
	}

	@Override
	public List<Shop> list() {
		ArrayList<Shop> rc = new ArrayList<Shop>();

		for (Deque<Shop> deque: m_data.values()) {
			rc.add(deque.peek());
		}

		return rc;
	}

}
