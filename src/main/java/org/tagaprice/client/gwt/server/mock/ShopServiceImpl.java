package org.tagaprice.client.gwt.server.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import org.tagaprice.client.gwt.shared.entities.*;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.*;
import org.tagaprice.client.gwt.shared.logging.*;
import org.tagaprice.client.gwt.shared.rpc.shopmanagement.*;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ShopServiceImpl extends RemoteServiceServlet implements IShopService  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4954618872880443049L;
	MyLogger _logger = LoggerFactory.getLogger(ShopServiceImpl.class);

	HashMap<IRevisionId, IShop> shopsAllRevisions = new HashMap<IRevisionId, IShop>();
	HashMap<Long, IRevisionId> newestRev = new HashMap<Long, IRevisionId>();
	Random random = new Random(1654196865);


	public ShopServiceImpl() {
		_logger.log("Load servlet...");


		//Create address for Shop(bills)
		ArrayList<IAddress> al1 = new ArrayList<IAddress>();
		IRevisionId r45665 = new RevisionId(random.nextLong(), 1);
		newestRev.put(r45665.getId(), r45665);
		al1.add(new Address(new RevisionId(random.nextLong(), 1), "Blumauergasse 1B", "1020", "Vienna", Country.at, 48.21906856732104, 16.38164520263672));

		IRevisionId r1598 = new RevisionId(random.nextLong(), 1);
		newestRev.put(r1598.getId(), r1598);
		IAddress a1 = new Address(new RevisionId(random.nextLong(), 1), "Holzhausergasse 9", "1020", "Vienna", Country.at, 48.21975481443672, 16.38885498046875);
		al1.add(a1);

		//Create some Shop
		IRevisionId r1 = new RevisionId(random.nextLong(), 1);
		newestRev.put(r1.getId(), r1);
		IShop s1 = new Shop(r1, "Billa");
		s1.setAddresses(al1);
		shopsAllRevisions.put(r1, s1);


		//2 shop
		//Create address for Shop(bills)
		ArrayList<IAddress> al2 = new ArrayList<IAddress>();
		IRevisionId r798654 = new RevisionId(random.nextLong(), 1);
		newestRev.put(r798654.getId(), r798654);
		al2.add(new Address(r798654, "Schüttelstraße 19A", "1020", "Vienna", Country.at, 48.21048970218907, 16.396751403808594));

		//Create some Shop
		IRevisionId r8998 = new RevisionId(random.nextLong(), 1);
		newestRev.put(r8998.getId(), r8998);
		IShop s8998 = new Shop(r8998, "Hofer");
		s8998.setAddresses(al2);
		shopsAllRevisions.put(r8998, s8998);

	}

	@Override
	public ArrayList<IShop> getShops(IShop searchCriteria) {
		_logger.log("getShops with IShop SearchCriteria ");

		ArrayList<IShop> result = new ArrayList<IShop>();

		for(IRevisionId rid:shopsAllRevisions.keySet())
			result.add(shopsAllRevisions.get(rid));


		return result;
	}

	@Override
	public IShop getShop(IRevisionId revisionId) {
		_logger.log("getShop with RevId " + revisionId);


		//get id from
		if(revisionId!=null){
			if(revisionId.getRevision()==0L){
				return shopsAllRevisions.get(newestRev.get(revisionId.getId()));
			}else{
				return shopsAllRevisions.get(revisionId);
			}


		}else{
			return null;
		}

	}

	@Override
	public IShop save(IShop shop) {
		_logger.log("save Shop " + shop);
		//shop.setRevisionId(new RevisionId(shop.getRevisionId().getId(), shop.getRevisionId().getRevision() + 1));

		//check revisionID
		//if shopID == 0L -> save as new Shop
		if(shop.getRevisionId() == null || shop.getRevisionId().getId()==0L){
			_logger.log("new Shop");

			for(IAddress ia:shop.getAddresses()){
				IRevisionId rt = new RevisionId(random.nextLong(), 1);
				ia.setRevisionId(rt);
				newestRev.put(rt.getId(), rt);
			}

			IRevisionId rt = new RevisionId(random.nextLong(), 1);
			shop.setRevisionId(rt);
			newestRev.put(rt.getId(), rt);

			shopsAllRevisions.put(rt, shop);

			return shop;

		}else{
			_logger.log("update shop");
			//UPDATE
			IShop updateShop = shopsAllRevisions.get(shop.getRevisionId());
			if(updateShop == null){
				//ERROR
				_logger.log("unexpected error");
				return null;
			}else{
				updateShop=shop;

				IRevisionId nr = updateShop.getRevisionId();
				nr.setRevision(nr.getRevision()+1);
				updateShop.setRevisionId(nr);


				shopsAllRevisions.put(updateShop.getRevisionId(), updateShop);
				newestRev.put(updateShop.getRevisionId().getId(), nr);

				for(IAddress ta:updateShop.getAddresses()){
					_logger.log("addr: "+ta.toString());


					if(ta.getRevisionID()==null || ta.getRevisionID().getId()==0L){
						_logger.log("create new address");


						IRevisionId rt = new RevisionId(random.nextLong(), 1);
						ta.setRevisionId(rt);
						newestRev.put(rt.getId(), rt);

					}else{
						_logger.log("update address");
						ta.getRevisionID().setRevision(ta.getRevisionID().getRevision()+1);
					}

				}

				return updateShop;
			}

		}

	}


}
