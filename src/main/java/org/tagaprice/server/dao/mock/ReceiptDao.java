package org.tagaprice.server.dao.mock;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

import org.tagaprice.server.dao.IReceiptDao;
import org.tagaprice.shared.entities.receiptManagement.Receipt;

public class ReceiptDao extends DaoClass<Receipt> implements IReceiptDao {
	@Override
	public List<Receipt> list() {
		ArrayList<Receipt> rc = new ArrayList<Receipt>();

		for (Deque<Receipt> deque: m_data.values()) {
			rc.add(deque.peek());
		}

		return rc;
	}

}
