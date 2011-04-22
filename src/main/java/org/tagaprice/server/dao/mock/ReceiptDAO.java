package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.tagaprice.server.dao.IDaoFactory;
import org.tagaprice.server.dao.IPackageDAO;
import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.server.dao.IShopDAO;
import org.tagaprice.shared.entities.receiptManagement.Receipt;

public class ReceiptDAO implements IReceiptDAO {

	IPackageDAO _packageDAO;
	IShopDAO _shopDAO;
	Random random = new Random(44646776);
	int productIdCounter = 1;
	HashMap<String, Receipt> _receiptList = new HashMap<String, Receipt>();
	//ArrayList<Receipt> _receiptList = new ArrayList<Receipt>();

	public ReceiptDAO(IDaoFactory daoFactory) {
		_packageDAO = daoFactory.getPackageDAO();
		_shopDAO = daoFactory.getShopDAO();

		System.out.println("_packDAO: "+_packageDAO);
		System.out.println("_shopDAO: "+_shopDAO);
		System.out.println("_unitDAO: "+daoFactory.getUnitDAO());
	}

	@Override
	public Receipt create(Receipt receipt) {
		String id = ""+random.nextInt();
		receipt.setId(id);
		receipt.setRevision("1");

		_receiptList.put(id, receipt);
		return receipt;
	}

	@Override
	public Receipt get(String id, String revision) {
		return _receiptList.get(id);
	}

	@Override
	public Receipt get(String id) {
		return get(id, null);
	}

	@Override
	public Receipt update(Receipt receipt) {
		receipt.setRevision(""+(Integer.parseInt(receipt.getRevision())+1));


		_receiptList.put(receipt.getId(), receipt);

		return receipt;
	}

	@Override
	public void delete(Receipt shop) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Receipt> list() {
		ArrayList<Receipt> _rw = new ArrayList<Receipt>();

		for (String key:_receiptList.keySet()){
			_rw.add(_receiptList.get(key));
		}

		return _rw;
	}

}
