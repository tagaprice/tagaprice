package org.tagaprice.client.features.receiptmanagement.createReceipt;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.client.generics.IView;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import com.google.gwt.place.shared.Place;

public interface ICreateReceiptView extends IView{

	/**
	 * Returns the Title of the {@link IReceipt}, if Title is NULL you get NULL back
	 * @return Returns the Title of the {@link IReceipt} or null.
	 */
	public String getTitle();

	/**
	 * Sets the entity title
	 * @param title Sets the entity title
	 */
	public void setTitle(String title);

	public Date getDate();

	public void setDate(Date date);

	public Shop getAddress();

	public void setAddress(Shop address);

	public BoundingBox getBoundingBox();

	public void setShopSearchResults(List<Shop> shopResults);

	public void setProductSearchResults(List<Product> productResults);

	public ArrayList<IReceiptEntry> getReceiptEntries();

	public void setReceiptEntries(ArrayList<IReceiptEntry> receiptEntries);

	/**
	 * Sets the {@link Presenter} which implements the {@link ICreateReceiptView} to control this view. It is also necessary
	 * for the {@link ICreateReceiptView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ICreateReceiptView} to control this view.
	 */
	public void setPresenter(Presenter presenter);


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
	}
}
