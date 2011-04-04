package org.tagaprice.client.gwt.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.Unit;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;

import com.google.gwt.user.client.ui.IsWidget;

public interface IPackageSelecter extends IsWidget {


	/**
	 * Set some {@link IPackage} to the Product.
	 * @param ipackage that will be added to the {@link IProduct}
	 */
	public void setPackages(ArrayList<IPackage> iPackage);


	/**
	 * Returns all packages includes in the IPackageSeleter.
	 * @return all packages includes in the IPackageSeleter.
	 */
	public ArrayList<IPackage> getPackages();

	/**
	 * Set the {@link Unit} where all other units must be related to. Is this is not set, all units are displayed
	 * @param unit Defines the {@link Unit} with which all other must be rated to.
	 */
	public void setRelatedUnit(Unit unit);


}
