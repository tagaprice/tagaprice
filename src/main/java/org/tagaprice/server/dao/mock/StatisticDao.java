package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.server.rpc.InitServlet;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;

public class StatisticDao extends DaoClass<StatisticResult> implements IStatisticDao {


	@Override
	public ArrayList<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end) {

		//Test Data
		ArrayList<StatisticResult> rc = new ArrayList<StatisticResult>();

		try {
			for(Receipt r:InitServlet.getDaoFactory().getReceiptDao().list()){
				if(bbox.contains(r.getShop().getAddress().getPos())){
					for(ReceiptEntry re:r.getReceiptEntries()){
						if(productId.equals(re.getPackage().getProduct().getId())){
							rc.add(new StatisticResult(
									r.getDate(),
									r.getShop(),
									null,
									re.getPackage().getQuantity(),
									re.getPrice()));
						}

					}
				}

			}
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return rc;
	}

	@Override
	public List<StatisticResult> searchPricesViaShop(String shopId, Date begin, Date end) {

		Log.debug("searchShopPrice");
		//TODO search
		//Test Data
		ArrayList<StatisticResult> rc = new ArrayList<StatisticResult>();

		try {
			for(Receipt r:InitServlet.getDaoFactory().getReceiptDao().list()){
				if(shopId.equals(r.getShop().getId())){
					for(ReceiptEntry re:r.getReceiptEntries()){
						rc.add(new StatisticResult(
								r.getDate(),
								null,
								re.getPackage().getProduct(),
								re.getPackage().getQuantity(),
								re.getPrice()));
					}
				}
			}
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return rc;
	}



}
