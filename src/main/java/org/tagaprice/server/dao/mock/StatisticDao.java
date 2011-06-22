package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.server.dao.IStatisticDao;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

public class StatisticDao extends DaoClass<StatisticResult> implements IStatisticDao {

	@Override
	public ArrayList<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StatisticResult> searchPricesViaShop(String shopId, BoundingBox bbox, Date begin, Date end) {
		// TODO Auto-generated method stub
		return null;
	}

}
