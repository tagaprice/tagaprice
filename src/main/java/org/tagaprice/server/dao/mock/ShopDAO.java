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
import org.tagaprice.shared.entities.shopmanagement.ISubsidiary;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.entities.shopmanagement.Subsidiary;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class ShopDAO implements IShopDAO {
	MyLogger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	HashMap<IRevisionId, IShop> shopsAllRevisions = new HashMap<IRevisionId, IShop>();
	HashMap<String, IRevisionId> newestRev = new HashMap<String, IRevisionId>();
	Random random = new Random(1654196865);

	public ShopDAO() {
		//Create address for Shop(bills)
		ArrayList<ISubsidiary> al1 = new ArrayList<ISubsidiary>();
		IRevisionId r45665 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r45665.getId(), r45665);
		al1.add(new Subsidiary(new RevisionId(new Long(random.nextLong()).toString(), "1"), new Address("Blumauergasse 1B", 48.21906856732104, 16.38164520263672)));

		IRevisionId r1598 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r1598.getId(), r1598);
		ISubsidiary a1 = new Subsidiary(new RevisionId(new Long(random.nextLong()).toString(), "1"), new Address("Holzhausergasse 9", 48.21975481443672, 16.38885498046875));
		al1.add(a1);

		//Create some Shops
		IRevisionId r1 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r1.getId(), r1);
		IShop s1 = new Shop(r1, "Billa");
		s1.setSubsidiary(al1);
		shopsAllRevisions.put(r1, s1);


		//2 shop
		//Create address for Shop(bills)
		ArrayList<ISubsidiary> al2 = new ArrayList<ISubsidiary>();
		IRevisionId r798654 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r798654.getId(), r798654);
		al2.add(new Subsidiary(r798654, new Address("Schüttelstraße 19A", 48.21048970218907, 16.396751403808594)));

		//Create some Shop
		IRevisionId r8998 = new RevisionId(new Long(random.nextLong()).toString(), "1");
		newestRev.put(r8998.getId(), r8998);
		IShop s8998 = new Shop(r8998, "Hofer");
		s8998.setSubsidiary(al2);
		shopsAllRevisions.put(r8998, s8998);

	}
	@Override
	public IShop create(IShop shop) {
		logger.log("new Shop");

		for(ISubsidiary ia:shop.getSubsidiaries()){
			IRevisionId rt = new RevisionId(new Long(random.nextLong()).toString(), "foo");
			ia.setRevisionId(rt);
			newestRev.put(rt.getId(), rt);
		}

		IRevisionId rt = new RevisionId(new Long(random.nextLong()).toString(), "foo");
		shop.setRevisionId(rt);
		newestRev.put(rt.getId(), rt);

		shopsAllRevisions.put(rt, shop);

		return shop;
	}

	@Override
	public IShop get(IRevisionId revisionId) {
		IShop rc = null;
		
		//get id from
		if(revisionId!=null) {
			if (revisionId.getRevision() == null) {
				return shopsAllRevisions.get(newestRev.get(revisionId.getId()));
			}else{
				return shopsAllRevisions.get(revisionId);
			}
		}

		return rc;
	}

	@Override
	public IShop update(IShop shop) {
		logger.log("update shop");
		//UPDATE
		IShop updateShop = shopsAllRevisions.get(shop.getRevisionId());
		if(updateShop == null){
			//ERROR
			logger.log("unexpected error");
			return null;
		}else{
			updateShop=shop;

			IRevisionId nr = updateShop.getRevisionId();
			nr.setRevision(nr.getRevision()+1);
			updateShop.setRevisionId(nr);


			shopsAllRevisions.put(updateShop.getRevisionId(), updateShop);
			newestRev.put(updateShop.getRevisionId().getId(), nr);

			for(ISubsidiary ta:updateShop.getSubsidiaries()){
				logger.log("addr: "+ta.toString());


				if(ta.getRevisionId()==null || ta.getRevisionId().getId()==null){
					logger.log("create new address");


					IRevisionId rt = new RevisionId(new Long(random.nextLong()).toString(), "1");
					ta.setRevisionId(rt);
					newestRev.put(rt.getId(), rt);

				}else{
					logger.log("update address");
					ta.getRevisionId().setRevision(ta.getRevisionId().getRevision()+1);
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
		// TODO Auto-generated method stub
		return null;
	}

}
