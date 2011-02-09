package org.tagaprice.client.gwt.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;
import org.tagaprice.core.entities.Unit;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Composite;

/**
 * This is an PackageSelecter wrapper class. This class implements the right screen design.
 * Is is also possible to use this class in UIBuilder
 * 
 */
public class PackageSelecter extends Composite implements IPackageSelecter {

	private IPackageSelecter packageSeleter = GWT.create(IPackageSelecter.class);

	public PackageSelecter() {
		initWidget(packageSeleter.asWidget());
	}


	@Override
	public void setPackages(ArrayList<IPackage> iPackage) {
		packageSeleter.setPackages(iPackage);

	}

	@Override
	public ArrayList<IPackage> getPackages() {
		return packageSeleter.getPackages();
	}


	@Override
	public void setRelatedUnit(Unit unit) {
		packageSeleter.setRelatedUnit(unit);
	}

}
