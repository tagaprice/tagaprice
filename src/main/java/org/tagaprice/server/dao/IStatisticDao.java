package org.tagaprice.server.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

public interface IStatisticDao extends IDaoClass<StatisticResult> {

	public ArrayList<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end);
	public List<StatisticResult> searchPricesViaShop(String shopId, BoundingBox bbox, Date begin, Date end);

}
