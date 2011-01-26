package org.tagaprice.client.gwt.server.diplomat.converter;

import org.slf4j.*;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.receiptManagement.*;
import org.tagaprice.core.entities.*;
import org.tagaprice.core.entities.ReceiptEntry;

public class ReceiptEntryConverter {

	private static final Logger _logger = LoggerFactory.getLogger(ReceiptEntryConverter.class);
	private static final ReceiptEntryConverter instance = new ReceiptEntryConverter();

	public static ReceiptEntryConverter getInstance() {
		return ReceiptEntryConverter.instance;
	}

	public IReceiptEntry convertCoreReceiptEntryToGWTReceiptEntry(ReceiptEntry coreReceiptEntry) {

		ProductConverter productConverter = ProductConverter.getInstance();

		IReceiptEntry gwtReceiptEntry = new org.tagaprice.client.gwt.shared.entities.receiptManagement.ReceiptEntry();

		long priceCentPerItem = 0;
		int quantity = 0;
		long receiptId = 0;
		IReceipt receipt = null;

		quantity = coreReceiptEntry.getCount();
		priceCentPerItem = coreReceiptEntry.getPricePerItem();

		IProduct gwtProduct = productConverter.convertCoreProductRevisionToGWTProduct(coreReceiptEntry.getProductRevision());

		gwtReceiptEntry.setProduct(gwtProduct);
		gwtReceiptEntry.setProductId(gwtProduct.getRevisionId());
		gwtReceiptEntry.setQuantity(quantity);
		gwtReceiptEntry.setPrice(priceCentPerItem);
		gwtReceiptEntry.setReceiptId(new RevisionId(receiptId));
		gwtReceiptEntry.setReceipt(receipt);

		return gwtReceiptEntry;
	}

	/**
	 * This Methode should only be called from ReceiptConverter so it will always be part of an full Receipt.
	 * @param gwtReceiptEntry
	 * @return
	 */
	public ReceiptEntry convertGWTReceiptEntryToCoreReceiptEntry(IReceiptEntry gwtReceiptEntry) {
		ReceiptEntry coreReceiptEntry;
		BasicShop basicShop = new BasicShop(0L, "");
		BasicReceipt basicReceipt = new BasicReceipt(gwtReceiptEntry.getReceipt().getRevisionId().getId(), basicShop, DefaultValues.defaultDate);

		ProductRevision productRevision = null;
		if(gwtReceiptEntry.getProduct() != null && gwtReceiptEntry.getProduct().getRevisionId() != null) {
			productRevision = new ProductRevision(gwtReceiptEntry.getProduct().getRevisionId().getId(), gwtReceiptEntry.getProduct().getRevisionId().getRevision(), "", null, null, null, 0., null, "");
		} else {
			ReceiptEntryConverter._logger.error("Product in GWTProduct should NEVER be null");
		}
		int count = gwtReceiptEntry.getQuantity();
		long pricePerItem = gwtReceiptEntry.getPrice();

		coreReceiptEntry = new ReceiptEntry(basicReceipt, productRevision, count, pricePerItem);

		return coreReceiptEntry;
	}

}
