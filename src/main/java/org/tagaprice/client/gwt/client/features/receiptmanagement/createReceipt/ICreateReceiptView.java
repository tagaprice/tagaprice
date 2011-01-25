package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import java.util.ArrayList;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IProduct;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import com.google.gwt.user.client.ui.IsWidget;


/**
 * This interface defines the create receipt View
 * This interface implements the interface isWidget and also defines the interface Presenter
 * which allows bidirectional communication with the CreateReceipts view
 * @author Helga Weik
 *
 * @param <T>
 */

public interface ICreateReceiptView<T> extends IsWidget{

	public void addEntry(ArrayList<T> entry);

	public void setPresenter(Presenter presenter);

	public void setSuggestProducts(ArrayList<IProduct>productList);


	public void setShops(ArrayList<IShop> shops);

	public String getProductName();

	public int getQuantity();

	public int getPrice();


	public interface Presenter {

		/**
		 * is called to add an entry to the list of bought items.
		 */
		public void onAddEntry();
		/**
		 * is called to save the receipt.
		 */
		public void onSave();

	}


}
