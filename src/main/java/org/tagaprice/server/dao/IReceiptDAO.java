package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.receiptManagement.IReceipt;


public interface IReceiptDAO extends IDAOClass<IReceipt> {
	public List<IReceipt> list();
}
