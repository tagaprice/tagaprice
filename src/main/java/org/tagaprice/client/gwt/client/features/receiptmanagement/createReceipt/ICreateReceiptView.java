package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import java.util.ArrayList;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.MultiWordSuggestOracle;


/**
 * This interface defines the create receipt View
 * This interface implements the interface isWidget and also defines the interface Presenter
 * which allows bidirectional communication with the CreateReceipts view
 * @author Helga Weik
 *
 * @param <T>
 */

public interface ICreateReceiptView<T> extends IsWidget{

	public void addReceiptEntry(ArrayList<T> entry);

	public void setPresenter(Presenter presenter);

	public void setSuggestProducts(MultiWordSuggestOracle productList);


	public void setShop(IShop shop);

	public IShop getShop();

	public String getProductName();

	public int getQuantity();

	public void setQuantity(int quantity);

	public long getPrice();

	public void setPrice(long price);

	public void setAvailableShops(ArrayList<IShop> availableShops);

	public void addShopChangeHanlder(ChangeHandler handler);

	public void shopsLoaded(String loaded);


	public interface Presenter {

		/**
		 * is called to add an entry to the list of bought items.
		 */
		public void onAddEntry();
		/**
		 * is called to save the receipt.
		 */
		public void onSave();
		public void onSaveEvent();
		public void onSelectProduct();

	}


}
