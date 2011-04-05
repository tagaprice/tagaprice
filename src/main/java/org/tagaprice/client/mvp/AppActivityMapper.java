package org.tagaprice.client.mvp;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.features.accountmanagement.login.LoginActivity;
import org.tagaprice.client.features.accountmanagement.login.LoginPlace;
import org.tagaprice.client.features.accountmanagement.register.RegisterActivity;
import org.tagaprice.client.features.accountmanagement.register.RegisterPlace;
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductActivity;
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.features.productmanagement.listProducts.*;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptActivity;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.ListReceiptsActivity;
import org.tagaprice.client.features.receiptmanagement.listReceipts.ListReceiptsPlace;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopActivity;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.features.shopmanagement.listShops.*;
import org.tagaprice.shared.logging.*;

import com.google.gwt.activity.shared.*;
import com.google.gwt.place.shared.Place;

/**
 * Maps Places to Activities.
 * 
 */
public class AppActivityMapper implements ActivityMapper {
	MyLogger logger = LoggerFactory.getLogger(AppActivityMapper.class);

	private ClientFactory clientFactory;

	public AppActivityMapper(ClientFactory clientFactory) {
		super();
		this.clientFactory = clientFactory;
	}

	@Override
	public Activity getActivity(Place place) {
		logger.log("I was asked for an activity...");
		// TODO make this gin
		if (place instanceof ListProductsPlace) {
			logger.log("return new ListProductsActivity");
			return new ListProductsActivity((ListProductsPlace) place, this.clientFactory);
		} else if (place instanceof CreateProductPlace) {
			logger.log("return new CreateProductActivity");
			return new CreateProductActivity((CreateProductPlace) place, this.clientFactory);
		}else if(place instanceof LoginPlace){
			logger.log("return new LoginActivity");
			return new LoginActivity((LoginPlace)place, this.clientFactory);
		}else if (place instanceof CreateShopPlace){
			logger.log("return new CreateShopActivity");
			return new CreateShopActivity((CreateShopPlace)place, this.clientFactory);
		} else if(place instanceof ListShopsPlace) {
			logger.log("return new ListShopsActivity");
			return new ListShopsActivity((ListShopsPlace) place, this.clientFactory);
		}else if(place instanceof CreateReceiptPlace){
			logger.log("return new CreateReceiptActivity");
			return new CreateReceiptActivity((CreateReceiptPlace)place, this.clientFactory);
		}else if(place instanceof RegisterPlace){
			logger.log("return new RegisterActivity");
			return new RegisterActivity((RegisterPlace)place, this.clientFactory);
		}if(place instanceof ListReceiptsPlace){
			logger.log("return new ListReceiptsActivity");
			return new ListReceiptsActivity((ListReceiptsPlace)place, this.clientFactory);
		}

		else {

			// THIS ELSE IS IMPORTANT TO AVOID FAILURES
			// IF THE PROGRAMER FORGOTT TO RETURN A VALUE
			return null;
		}
	}

}