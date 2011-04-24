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
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

public class ReceiptDAO implements IReceiptDAO {
	MyLogger _logger = LoggerFactory.getLogger(ReceiptDAO.class);
	IPackageDAO _packageDAO;
	IShopDAO _shopDAO;
	Random random = new Random(99746776);
	int productIdCounter = 1;
	HashMap<String, Receipt> _receiptList = new HashMap<String, Receipt>();
	//ArrayList<Receipt> _receiptList = new ArrayList<Receipt>();

	public ReceiptDAO(IDaoFactory daoFactory) {
	}

	@Override
	public Receipt create(Receipt receipt) {
		_logger.log("create receipt: "+receipt);
		String id = ""+random.nextInt();
		receipt.setId(id);
		receipt.setRevision("1");
		_receiptList.put(id, receipt);
		_logger.log("created receipt: "+receipt);
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
		_logger.log("create receipt: "+receipt);
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
		_logger.log("list ");
		ArrayList<Receipt> _rw = new ArrayList<Receipt>();

		for (String key:_receiptList.keySet()){
			_rw.add(_receiptList.get(key));
		}

		return _rw;
	}

}
