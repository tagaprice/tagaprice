package org.tagaprice.server.dao.couchdb;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.exceptions.dao.DaoException;

public class StatisticDao extends DaoClass<StatisticResult> implements IStatisticDao {

	private IReceiptDao m_receiptDao;

	public StatisticDao(CouchDbDaoFactory daoFactory) {
		super(daoFactory, StatisticResult.class, "statistic", daoFactory._getDocumentDao());
		m_receiptDao = daoFactory.getReceiptDao();
	}

	@Override
	public ArrayList<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end) {
		//TODO Search efficient via db or elasticsearch
		//Test Data
		ArrayList<StatisticResult> rc = new ArrayList<StatisticResult>();

		try {
			for(Receipt r:m_receiptDao.list()){
				if(r.getShop().getAddress().getLat()<bbox.getNorthEastLat() &&
						r.getShop().getAddress().getLat()>bbox.getSouthWestLat() &&
						r.getShop().getAddress().getLon()<bbox.getNorthEastLon() &&
						r.getShop().getAddress().getLon()>bbox.getSouthWestLon()){
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
	public List<StatisticResult> searchPricesViaShop(String shopId, BoundingBox bbox, Date begin, Date end) {
		//TODO Search efficient via db or elasticsearch
		//Test Data
		ArrayList<StatisticResult> rc = new ArrayList<StatisticResult>();

		try {
			for(Receipt r:m_receiptDao.list()){
				if(r.getShop().getAddress().getLat()<bbox.getNorthEastLat() &&
						r.getShop().getAddress().getLat()>bbox.getSouthWestLat() &&
						r.getShop().getAddress().getLon()<bbox.getNorthEastLon() &&
						r.getShop().getAddress().getLon()>bbox.getSouthWestLon()){

					if(shopId.equals(r.getShopId())){
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

			}
		} catch (DaoException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return rc;
	}




}
