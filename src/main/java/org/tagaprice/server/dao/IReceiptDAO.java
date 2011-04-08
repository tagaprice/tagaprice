package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.receiptManagement.Receipt;

public interface IReceiptDAO extends IDAOClass<Receipt> {
	public List<Receipt> list();
}
