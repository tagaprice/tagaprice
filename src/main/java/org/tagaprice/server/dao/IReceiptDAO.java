package org.tagaprice.server.dao;

import java.util.List;

import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.receiptManagement.IReceipt;


public interface IReceiptDAO {
	public IReceipt create(IReceipt receipt);
	public IReceipt get(IRevisionId id);
	public IReceipt update(IReceipt receipt);
	public void delete(IReceipt receipt);
	public List<IReceipt> list();
}
