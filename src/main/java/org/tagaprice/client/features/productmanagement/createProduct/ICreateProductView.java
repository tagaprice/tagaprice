package org.tagaprice.client.features.productmanagement.createProduct;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.generics.IView;
import org.tagaprice.shared.entities.IRevisionId;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.dump.ICategory;
import org.tagaprice.shared.entities.productmanagement.IPackage;

import com.google.gwt.place.shared.Place;

/**
 * This interface is necessary to implement a ProductManagementView
 * 
 */
public interface ICreateProductView extends IView {

	public void setRevisionId(IRevisionId revisionId);

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
	public String getProductTitle();




	/**
	 * Sets the {@link Unit} which a {@link org.tagaprice.shared.entities.productmanagement.IProduct} can
	 * have.
	 * 
	 * @param unit
	 *            the {@link Unit} which a {@link org.tagaprice.shared.entities.productmanagement.IProduct}
	 *            can
	 *            have.
	 */
	public void setUnit(Unit unit);

	/**
	 * The {@link Unit} in which a {@link org.tagaprice.shared.entities.productmanagement.IProduct} can be
	 * bought.
	 * 
	 * @return the {@link Unit} in which a {@link org.tagaprice.shared.entities.productmanagement.IProduct} can be
	 * bought
	 */
	public Unit getUnit();

	/**
	 * Sets the depending {@link ICategory} for a
	 * {@link org.tagaprice.shared.entities.productmanagement.IProduct}
	 * 
	 * @param category
	 *            the depending {@link ICategory} for a
	 *            {@link org.tagaprice.shared.entities.productmanagement.IProduct}
	 */
	public void setCategory(ICategory category);

	/**
	 * Returns the depending {@link ICategory}
	 * 
	 * @return Returns the depending {@link ICategory}
	 */
	public ICategory getCategory();

	/**
	 * Add one {@link IPackage} to the Product.
	 * @param ipackage that will be added to the {@link IProduct}
	 */
	public void addPackage(IPackage ipackage);

	/**
	 * Add some {@link IPackage} to the Product.
	 * @param ipackage that will be added to the {@link IProduct}
	 */
	public void setPackages(ArrayList<IPackage> iPackage);

	/**
	 * Return all Packages includes in a {@link IProduct}
	 * @return all Packages includes in a {@link IProduct}
	 */
	public ArrayList<IPackage> getPackages();

	/**
	 * Sets the {@link Presenter} which implements the {@link IProductView} to control this view. It is also necessary
	 * for the {@link IProductView} to communicate with the {@link Presenter}
	 * 
	 * @param presenter
	 *            Sets the {@link Presenter} which implements the {@link IProductView} to control this view.
	 */
	public void setPresenter(Presenter presenter);

	public void setAvailableCategories(List<ICategory> categories);

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
	}

}
