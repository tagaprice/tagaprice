package org.tagaprice.server.dao;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IStatisticDao extends IDaoClass<StatisticResult> {

	public List<StatisticResult> searchPricesViaProduct(String productId, BoundingBox bbox, Date begin, Date end) throws DaoException;
	public List<StatisticResult> searchPricesViaShop(String shopId, Date begin, Date end) throws DaoException;
	public List<StatisticResult> searchPricesViaShopCategory(String categoryId, BoundingBox bbox, Date begin, Date end) throws DaoException;
	public List<StatisticResult> searchPricesVieProductCategory(String categoryId, BoundingBox bbox, Date begin, Date end) throws DaoException;
}
