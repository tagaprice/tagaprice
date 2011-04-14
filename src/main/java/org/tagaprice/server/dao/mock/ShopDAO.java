package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class ShopDAO implements IShopDAO {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	HashMap<String, Shop> shopsAllRevisions = new HashMap<String, Shop>();
	HashMap<String, String> newestRev = new HashMap<String, String>();
	Random random = new Random(1654196865);

	public ShopDAO() {
		//Create address for Shop(bills)
		String r45665 = new Long(random.nextLong()).toString();
		newestRev.put(r45665, r45665);

		String r1598 = new Long(random.nextLong()).toString();
		newestRev.put(r1598, r1598);

		//Create some Shops
		String r1 = new Long(random.nextLong()).toString();
		newestRev.put(r1, r1);
		Shop s1 = new Shop(r1, "1", "Billa1");
		s1.setAddress(new Address("Blumauergasse 1B", 48.21906856732104, 16.38164520263672));
		
		shopsAllRevisions.put(r1, s1);


		//2 shop
		//Create address for Shop(bills)
		String r798654 = new Long(random.nextLong()).toString();
		newestRev.put(r798654, r798654);

		//Create some Shop
		String r8998 = new Long(random.nextLong()).toString();
		newestRev.put(r8998, r8998);
		Shop s8998 = new Shop(r8998, "1", "Hofer");
		s8998.setAddress(new Address("Schüttelstraße 19A", 48.21048970218907, 16.396751403808594));

		shopsAllRevisions.put(r8998, s8998);

	}
	@Override
	public Shop create(Shop shop) {
		logger.log("new Shop");

		shop.setId(new Long(random.nextLong()).toString());
		shop.setRevision("1");
		newestRev.put(shop.getId(), shop.getId());

		shopsAllRevisions.put(shop.getId(), shop);

		return shop;
	}

	@Override
	public Shop get(String id, String revision) {
		Shop rc = null;

		//get id from
		if(id!=null) {
			if (revision == null) {
				return shopsAllRevisions.get(newestRev.get(id));
			}else{
				return shopsAllRevisions.get(id);
			}
		}

		return rc;
	}

	@Override
	public Shop get(String id) {
		return get(id, null);
	}

	@Override
	public Shop update(Shop shop) {
		logger.log("update shop");
		//UPDATE
		Shop updateShop = shopsAllRevisions.get(shop.getId());
		if(updateShop == null){
			//ERROR
			logger.log("unexpected error");
			return null;
		}else{
			updateShop=shop;

			updateShop.setRevision(new Integer(Integer.parseInt(updateShop.getRevision())+1).toString());

			shopsAllRevisions.put(updateShop.getId(), updateShop);
			newestRev.put(updateShop.getId(), updateShop.getId());

			return updateShop;
		}
	}

	@Override
	public void delete(Shop shop) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Shop> list() {
		ArrayList<Shop> list = new ArrayList<Shop>();
		for(String s:shopsAllRevisions.keySet())
			list.add(shopsAllRevisions.get(s));
		return list;
	}

}
