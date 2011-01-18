package org.tagaprice.server.dao.interfaces;

import java.util.List;

import org.tagaprice.core.entities.ReceiptEntry;

public interface IReceiptDAO {

	/**
	 * 
	 * @param productId id of the product to get the {@link ReceiptEntry}s for.
	 * @param rev revision of the product.
	 * @return all {@link ReceiptEntry}s sorted by date
	 */
	List<ReceiptEntry> getReceiptEntriesByProductIdAndRev(long productId, int rev);

}
