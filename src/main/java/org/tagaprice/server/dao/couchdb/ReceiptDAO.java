package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.shared.entities.receiptManagement.IReceipt;

public class ReceiptDAO extends DAOClass<IReceipt> implements IReceiptDAO {
	public ReceiptDAO() {
		super(IReceipt.class, "receipt");
	}
	
	@Override
	public List<IReceipt> list() {
		throw new UnsupportedOperationException("ReceiptDAO.list() wasn't implemented yet");
	}

}
