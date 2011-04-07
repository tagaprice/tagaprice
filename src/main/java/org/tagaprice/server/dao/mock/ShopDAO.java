package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.server.rpc.ProductServiceImpl;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.RevisionId;
import org.tagaprice.shared.entities.shopmanagement.IShop;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class ShopDAO implements IShopDAO {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	HashMap<IRevisionId, IShop> shopsAllRevisions = new HashMap<IRevisionId, IShop>();
	HashMap<String, IRevisionId> newestRev = new HashMap<String, IRevisionId>();
	Random random = new Random(1654196865);

	public ShopDAO() {
		//Create address for Shop(bills)
		ArrayList<IShop> al1 = new ArrayList<IShop>();
		IRevisionId r45665 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r45665.getId(), r45665);
		{
			IShop is = new Shop(new Long(random.nextLong()).toString(), "1", "Billa - Blumauergasse 1B");
			is.setAddress(new Address("Blumauergasse 1B", 48.21906856732104, 16.38164520263672));
			al1.add(is);
		}

		IRevisionId r1598 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r1598.getId(), r1598);

		{
			IShop is = new Shop(new Long(random.nextLong()).toString(), "1", "Billa - Holzhausergasse 9");
			is.setAddress(new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
			al1.add(is);
		}

		//Create some Shops
		IRevisionId r1 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r1.getId(), r1);
		IShop s1 = new Shop(r1.getId(), r1.getRevision(), "Billa");
		s1.setChilds(al1);
		shopsAllRevisions.put(r1, s1);


		//2 shop
		//Create address for Shop(bills)
		ArrayList<IShop> al2 = new ArrayList<IShop>();
		IRevisionId r798654 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r798654.getId(), r798654);
		{
			IShop is = new Shop(new Long(random.nextLong()).toString(), "1", "Hofer - Schüttelstraße 19A");
			is.setAddress(new Address("Schüttelstraße 19A", 48.21048970218907, 16.396751403808594));
			al2.add(is);
		}


		//Create some Shop
		IRevisionId r8998 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r8998.getId(), r8998);
		IShop s8998 = new Shop(r8998.getId(), r8998.getRevision(), "Hofer");
		s8998.setChilds(al2);
		shopsAllRevisions.put(r8998, s8998);

	}
	@Override
	public IShop create(IShop shop) {
		logger.log("new Shop");

		for(IShop ia:shop.getChilds()){
			ia.setId(new Long(random.nextLong()).toString());
			ia.setRevision("1");
			newestRev.put(ia.getId(), new RevisionId(ia.getId(), ia.getRevision()));
		}

		shop.setId(new Long(random.nextLong()).toString());
		shop.setRevision("1");
		newestRev.put(shop.getId(), new RevisionId(shop.getId(), shop.getRevision()));

		shopsAllRevisions.put(new RevisionId(shop.getId(), shop.getRevision()), shop);

		return shop;
	}

	@Override
	public IShop get(String id, String revision) {
		IShop rc = null;

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
	public IShop get(String id) {
		return get(id, null);
	}

	@Override
	public IShop update(IShop shop) {
		logger.log("update shop");
		//UPDATE
		IShop updateShop = shopsAllRevisions.get(new RevisionId(shop.getId(), shop.getRevision()));
		if(updateShop == null){
			//ERROR
			logger.log("unexpected error");
			return null;
		}else{
			updateShop=shop;

			updateShop.setRevision(new Integer(Integer.parseInt(updateShop.getRevision())+1).toString());

			shopsAllRevisions.put(new RevisionId(updateShop.getId(), updateShop.getRevision()), updateShop);
			newestRev.put(updateShop.getId(), new RevisionId(updateShop.getId(), updateShop.getRevision()));

			for(IShop ta:updateShop.getChilds()){
				logger.log("addr: "+ta.toString());


				if (ta.getId()==null) {
					logger.log("create new address");

					ta.setId(new Long(random.nextLong()).toString());
					ta.setRevision("1");

					newestRev.put(ta.getId(), new RevisionId(ta.getId(), ta.getRevision()));
				}else{
					logger.log("update address");
					ta.setRevision(new Integer(Integer.parseInt(ta.getRevision())+1).toString());
				}

			}

			return updateShop;
		}
	}

	@Override
	public void delete(IShop shop) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IShop> list() {
		ArrayList<IShop> list = new ArrayList<IShop>();
		for(IRevisionId s:shopsAllRevisions.keySet())
			list.add(shopsAllRevisions.get(s));
		return list;
	}

}
