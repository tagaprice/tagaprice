package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;

public interface IReceiptDAO {

	/**
	 * 
	 * @param productId id of the product to get the {@link ReceiptEntry}s for.
	 * @param productRevision revision number of the product.
	 * @return all {@link ReceiptEntry}s sorted by date
	 */
	List<ReceiptEntry> getReceiptEntriesByProductIdAndRev(long productId, int productRevision);

	/**
	 * Save a {@link Receipt} and all its {@link ReceiptEntry}s.
	 */
	Receipt save(Receipt receipt);

}
