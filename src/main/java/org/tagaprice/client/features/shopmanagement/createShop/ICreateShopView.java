package org.tagaprice.client.features.shopmanagement.createShop;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

public interface ICreateShopView extends IsWidget {

	public Address getAddress();

	public Shop getBranding();

	
	/**
	 * Sets the depending {@link Category} for a
	 * {@link Product}
	 * 
	 * @param category
	 *            the depending {@link Category} for a
	 *            {@link Product}
	 */
	public void setCategory(Category category);

	/**
	 * Returns the depending {@link Category}
	 * 
	 * @return Returns the depending {@link Category}
	 */
	public Category getCategory();
	
	/**
	 * Set current address (Position of the user)
	 * @param address address (position) of the user
	 */
	public void setAddress(Address address);

	public void setBranding(Shop branding);

	public void setBrandingSearchResults(List<Shop> results);


	/**
	 * Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view. It is also necessary
	 * for the {@link ICreateShopView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link ICreateShopView} to control this view.
	 */
	public void setPresenter(Presenter presenter);


	/**
	 * Set statistic results
	 * @param results statistic results.
	 */
	public void setStatisticResults(List<StatisticResult> results);

	public void setTitle(String title);

	public String getTitle();
	
	/**
	 * Set the readmode of the view
	 * @param read
	 */
	public void setReadOnly(boolean read);

	
	/**
	 * 
	 * @return Returns the bounding box of the Statistic map
	 */
	public BoundingBox getStatisticBoundingBox();
	
	/**
	 * 
	 * @return The selected begin date. Is current date if nothing has been selected.
	 */
	public Date getStatisticBeginDate();
	
	/**
	 * 
	 * @return The selected end date. Is current date if nothing has been selected.
	 */
	public Date getStatisticEndDate();
	
	
	/**
	 * Set the center of the statistic Widget. Should be the current position of the user.
	 * @param lat
	 * @param lon
	 */
	public void setStatisticLatLon(double lat, double lon);
	
	/**
	 * 
	 *
	 */
	public interface Presenter {
		public void brandingSearch(String search);


		/**
		 * Is used by the {@link org.tagaprice.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);


		/**
		 * This event is called when the user has CHANGED/CREATED a
		 * shop.
		 * 
		 * @param event
		 *            is called when the user has CHANGED/CREATED a
		 *            shop.
		 */
		public void onSaveEvent();

		/**
		 * 	This event is called when the user has changed something at the statistic widget
		 * @param bbox BBox to search for Shop
		 * @param begin start Date
		 * @param end end Date
		 */
		public void onStatisticChangedEvent(BoundingBox bbox, Date begin, Date end);

		/**
		 * This event is called when the user has CHANGED the Category
		 * TODO: Implement event
		 */
		public void onCategorySelectedEvent();
		
		/**
		 * Is sent if category has been clicked
		 * @param categoryId
		 */
		public void onCategoryClicked(String categoryId);
	}


	
}
