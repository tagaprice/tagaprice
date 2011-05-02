package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.receiptManagement.Receipt;
import org.tagaprice.shared.exceptions.dao.DaoException;

public interface IReceiptDAO extends IDaoClass<Receipt> {
	public List<Receipt> list() throws DaoException;
}
