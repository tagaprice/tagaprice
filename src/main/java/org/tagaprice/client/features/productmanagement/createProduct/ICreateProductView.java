package org.tagaprice.client.features.productmanagement.createProduct;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.IsWidget;

/**
 * This interface is necessary to implement a ProductManagementView
 * 
 */
public interface ICreateProductView extends IsWidget {


	/**
	 * Sets the displayed title
	 * 
	 * @param title
	 *            the displayed title
	 */
	public void setTitle(String title);

	/**
	 * Returns the currently displayed title.
	 * 
	 * @return the currently displayed title
	 */
	public String getTitle();




	/**
	 * Sets the {@link Unit} which a {@link Product} can
	 * have.
	 * 
	 * @param unit
	 *            the {@link Unit} which a {@link Product}
	 *            can
	 *            have.
	 */
	public void setUnit(Unit unit);

	/**
	 * The {@link Unit} in which a {@link Product} can be
	 * bought.
	 * 
	 * @return the {@link Unit} in which a {@link Product} can be
	 * bought
	 */
	public Unit getUnit();

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
	 * Add one {@link Package} to the Product.
	 * @param ipackage that will be added to the {@link Product}
	 */
	public void addPackage(Package ipackage);

	/**
	 * Add some {@link Package} to the Product.
	 * @param ipackage that will be added to the {@link Product}
	 */
	public void setPackages(ArrayList<Package> iPackage);

	/**
	 * Return all Packages includes in a {@link Product}
	 * @return all Packages includes in a {@link Product}
	 */
	public ArrayList<Package> getPackages();


	/**
	 * Set statistic results
	 * @param results statistic results.
	 */
	public void setStatisticResults(List<StatisticResult> results);

	/**
	 * Sets the {@link Presenter} which implements the {@link IProductView} to control this view. It is also necessary
	 * for the {@link IProductView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link IProductView} to control this view.
	 */
	public void setPresenter(Presenter presenter);


	/**
	 * 
	 *
	 */
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
		 * This event is called when the user has CHANGED/CREATED a
		 * {@link org.tagaprice.shared.entities.productmanagement.Product}.
		 */
		public void onSaveEvent();

		/**
		 * This event is called when the user has CHANCED the title
		 * 
		 * TODO: Implement event
		 */
		public void onTitleSelectedEvent();

		/**
		 * This event is called when the user has CHANGED the Unit
		 * TODO: Implement event
		 */
		public void onUnitSelectedEvent();

		/**
		 * This event is called when the user has CHANGED the Category
		 * TODO: Implement event
		 */
		public void onCategorySelectedEvent();

		/**
		 * 	This event is called when the user has changed something at the statistic widget
		 * @param bbox BBox to search for Shop
		 * @param begin start Date
		 * @param end end Date
		 */
		public void onStatisticChangedEvent(BoundingBox bbox, Date begin, Date end);
	}

}
