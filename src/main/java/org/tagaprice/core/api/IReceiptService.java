package org.tagaprice.core.api;

import java.util.List;

import org.tagaprice.core.entities.Receipt;
import org.tagaprice.core.entities.ReceiptEntry;


public interface IReceiptService {
	/**
	 * Attempts to save given receipt.
	 * 
	 * @throws ServerException
	 *             Thrown to indicate that the Server has failed handling the latest request.
	 */
	Receipt save(Receipt receipt) throws ServerException;

	/**
	 * @param productId
	 *            id of the product to get the {@link ReceiptEntry}s for.
	 * @param productRevision
	 *            revision number of the product.
	 * 
	 * @return all {@link ReceiptEntry}s for the specified product revision.
	 * 
	 * @throws ServerException
	 *             Thrown to indicate that the Server has failed handling the latest request.
	 * @throws IllegalRevisionException 
	 */
	List<ReceiptEntry> getReceiptEntriesByProductIdAndRev(Long productId, Integer productRevision) throws ServerException, IllegalRevisionException;
}
