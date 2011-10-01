package org.tagaprice.client.mvp;

import org.tagaprice.client.features.accountmanagement.inviteFriends.InviteFriendsPlace;
import org.tagaprice.client.features.accountmanagement.settings.SettingsPlace;
import org.tagaprice.client.features.categorymanagement.product.ProductCategoryPlace;
import org.tagaprice.client.features.categorymanagement.shop.ShopCategoryPlace;
import org.tagaprice.client.features.productmanagement.createProduct.CreateProductPlace;
import org.tagaprice.client.features.receiptmanagement.createReceipt.CreateReceiptPlace;
import org.tagaprice.client.features.receiptmanagement.listReceipts.ListReceiptsPlace;
import org.tagaprice.client.features.searchmanagement.SearchPlace;
import org.tagaprice.client.features.shopmanagement.createShop.CreateShopPlace;
import org.tagaprice.client.features.startmanagement.StartPlace;

import com.google.gwt.place.shared.*;

/**
 * Some GWT Magic
 * 
 */

@WithTokenizers({
	CreateProductPlace.Tokenizer.class,
	CreateShopPlace.Tokenizer.class,
	CreateReceiptPlace.Tokenizer.class,
	ListReceiptsPlace.Tokenizer.class,
	StartPlace.Tokenizer.class,
	SearchPlace.Tokenizer.class,
	ProductCategoryPlace.Tokenizer.class,
	ShopCategoryPlace.Tokenizer.class,
	SettingsPlace.Tokenizer.class,
	InviteFriendsPlace.Tokenizer.class})
	public interface AppPlaceHistoryMapper extends PlaceHistoryMapper {

}
