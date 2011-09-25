package org.tagaprice.client.features.categorymanagement;

import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.place.shared.Place;


public interface ICategoryView extends IsWidget {

	/**
	 * Tell the view that it is going to be destroyed or set invisible.
	 */
	public void onStop();
	
	
	public void setPresenter(Presenter presenter);
	
	
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
	 * Set statistic results
	 * @param results statistic results.
	 */
	public void setStatisticResults(List<StatisticResult> results);
	
	/**
	 * Set the center of the statistic Widget. Should be the current position of the user.
	 * @param lat
	 * @param lon
	 */
	public void setStatisticLatLon(double lat, double lon);
	
	
	/**
	 * Tell the statistic widget that activity is loading data from the server
	 */
	public void setStatisticIsLoading();
	
	
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
	
	public interface Presenter{
		
		/**
		 * Is used by the {@link org.tagaprice.client.mvp.AppActivityMapper} to display a new place in the
		 * browser window.
		 * 
		 * @param place
		 *            The {@link Place} which should be displayed next.
		 */
		public void goTo(Place place);
		
		
		/**
		 * 	This event is called when the user has changed something at the statistic widget
		 * @param bbox BBox to search for Shop
		 * @param begin start Date
		 * @param end end Date
		 */
		public void onStatisticChangedEvent(BoundingBox bbox, Date begin, Date end);
	
	
		public void onCategoryClicked(String categoryId);
	}
}
