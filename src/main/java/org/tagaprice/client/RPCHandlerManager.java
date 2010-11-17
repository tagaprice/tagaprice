package org.tagaprice.client;

import org.tagaprice.shared.rpc.LocalAccountHandler;
import org.tagaprice.shared.rpc.LocalAccountHandlerAsync;
import org.tagaprice.shared.rpc.PriceHandler;
import org.tagaprice.shared.rpc.PriceHandlerAsync;
import org.tagaprice.shared.rpc.ProductHandler;
import org.tagaprice.shared.rpc.ProductHandlerAsync;
import org.tagaprice.shared.rpc.ReceiptHandler;
import org.tagaprice.shared.rpc.ReceiptHandlerAsync;
import org.tagaprice.shared.rpc.SearchHandler;
import org.tagaprice.shared.rpc.SearchHandlerAsync;
import org.tagaprice.shared.rpc.ShopHandler;
import org.tagaprice.shared.rpc.ShopHandlerAsync;
import org.tagaprice.shared.rpc.CategoryHandler;
import org.tagaprice.shared.rpc.CategoryHandlerAsync;
import org.tagaprice.shared.rpc.UnitHandler;
import org.tagaprice.shared.rpc.UnitHandlerAsync;

import com.google.gwt.core.client.GWT;

public class RPCHandlerManager {

	private static CategoryHandlerAsync typeHandler;
	private static ProductHandlerAsync productHandler;
	private static ReceiptHandlerAsync receiptHandler;
	private static PriceHandlerAsync priceHandler;
	private static SearchHandlerAsync searchHandler;
	private static ShopHandlerAsync shopHandler;
	private static UnitHandlerAsync unitHandler;
	private static LocalAccountHandlerAsync localAccountHandler;

	

	public static UnitHandlerAsync getUnitHandler() {
		if(unitHandler==null)
			unitHandler = GWT.create(UnitHandler.class);
		return unitHandler;
	}


	public static LocalAccountHandlerAsync getLocalAccountHandler() {
		if(localAccountHandler==null)
			localAccountHandler = GWT.create(LocalAccountHandler.class);
		return localAccountHandler;
	}


	public static CategoryHandlerAsync getTypeHandler() {
		if(typeHandler==null)
			typeHandler = GWT.create(CategoryHandler.class);		
		return typeHandler;
	}


	public static ProductHandlerAsync getProductHandler() {
		if(productHandler==null)
			productHandler = GWT.create(ProductHandler.class);	
		return productHandler;
	}


	public static ReceiptHandlerAsync getReceiptHandler() {
		if(receiptHandler==null)
			receiptHandler = GWT.create(ReceiptHandler.class);
		return receiptHandler;
	}


	public static PriceHandlerAsync getPriceHandler() {
		if(priceHandler==null)
			priceHandler = GWT.create(PriceHandler.class);
		return priceHandler;
	}


	public static SearchHandlerAsync getSearchHandler() {
		if(searchHandler==null)
			searchHandler = GWT.create(SearchHandler.class);
		return searchHandler;
	}


	public static ShopHandlerAsync getShopHandler() {
		if(shopHandler==null)
			shopHandler = GWT.create(ShopHandler.class);
		return shopHandler;
	}
	
	
	
}
