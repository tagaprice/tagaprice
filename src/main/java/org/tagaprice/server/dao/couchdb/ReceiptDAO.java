package org.tagaprice.server.dao.couchdb;

import java.util.List;

import org.tagaprice.server.dao.IReceiptDAO;
import org.tagaprice.shared.entities.receiptManagement.Receipt;

public class ReceiptDAO extends DAOClass<Receipt> implements IReceiptDAO {
	public ReceiptDAO() {
		super(Receipt.class, "receipt");
	}
	
	@Override
	public List<Receipt> list() {
		throw new UnsupportedOperationException("ReceiptDAO.list() wasn't implemented yet");
	}

}
