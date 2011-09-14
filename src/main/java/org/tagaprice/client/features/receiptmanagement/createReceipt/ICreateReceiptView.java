package org.tagaprice.client.features.receiptmanagement.createReceipt;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.ReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ICreateReceiptView extends IsWidget{

	/**
	 * Returns the Title of the {@link Receipt}, if Title is NULL you get NULL back
	 * @return Returns the Title of the {@link Receipt} or null.
	 */
	public String getNote();

	public void setNote(String note);

	public Date getDate();

	public void setDate(Date date);

	public Shop getShop();

	public void setShop(Shop shop);

	public void setAddress(Address address);


	public BoundingBox getBoundingBox();

	public void setShopSearchResults(List<Shop> shopResults);

	public void setProductSearchResults(List<Product> productResults);

	public List<ReceiptEntry> getReceiptEntries();

	public void setReceiptEntries(List<ReceiptEntry> receiptEntries);

	/**
	 * Sets the {@link Presenter} which implements the {@link ICreateReceiptView} to control this view. It is also necessary
	 * for the {@link ICreateReceiptView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ICreateReceiptView} to control this view.
	 */
	public void setPresenter(Presenter presenter);


	public void setSelectableAddress(List<Address> address);
	
	
	public interface Presenter {

		/**
		 * Is used by the {@link org.tagaprice.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);

		/**
		 * Call this method when the shop search string has been changed.
		 * @param shopSearch current search string
		 */
		public void shopSearchStringHasChanged(String shopSearch);

		/**
		 * Call this method when the product search string has been changed.
		 * @param productSearch current search string
		 */
		public void productSearchStringHasChanged(String productSearch);

		/**
		 * Call if savebutton has been clicked
		 */
		public void onSaveEvent();

		/**
		 * Get the current id of the receipt or null
		 * @return
		 */
		public String getId();
		
		
		public void checkSave();
		
		public void onFoundPositionBySearchQuery(Address address);
		
		
		public void onLogout();
	}
}
