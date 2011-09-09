package org.tagaprice.server.dao;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IReceiptDao extends IDaoClass<Receipt> {
	public List<Receipt> list() throws DaoException;

	List<Receipt> listByPackage(String packageId) throws DaoException;

	List<Receipt> listByUser(String userId) throws DaoException;

	List<Receipt> listByShop(String shopId, Date from, Date to) throws DaoException;
}
