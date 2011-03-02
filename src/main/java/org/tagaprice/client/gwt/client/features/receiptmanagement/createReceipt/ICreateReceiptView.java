package org.tagaprice.client.gwt.client.features.receiptmanagement.createReceipt;

import java.util.ArrayList;
import java.util.Date;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IAddress;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ICreateReceiptView extends IsWidget{

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

	public IAddress getAddress();

	public void setAddress(IAddress address);


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
		 * Is used by the {@link org.tagaprice.client.gwt.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);
	}
}
