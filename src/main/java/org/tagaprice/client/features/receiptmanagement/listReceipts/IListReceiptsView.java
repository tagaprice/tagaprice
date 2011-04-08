package org.tagaprice.client.features.receiptmanagement.listReceipts;

import java.util.List;

import com.google.gwt.place.shared.Place;
import org.tagaprice.client.generics.IView;
import org.tagaprice.shared.entities.receiptManagement.Receipt;

public interface IListReceiptsView extends IView {

	/**
	 * Display a receipt list
	 * @param receipts all receipts of this user.
	 */
	public void setReceipts(List<Receipt> receipts);

	/**
	 * Sets the {@link Presenter} which implements the {@link IListReceiptsView} to control this view. It is also necessary
	 * for the {@link IListReceiptsView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link IListReceiptsView} to control this view.
	 */
	public void setPresenter(Presenter presenter);

	public interface Presenter{

		/**
		 * Is used by the {@link org.tagaprice.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);
	}
}
