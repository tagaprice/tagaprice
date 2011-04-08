package org.tagaprice.client.generics.widgets;

import java.util.ArrayList;

import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.productmanagement.Package;

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
	public void setPackages(ArrayList<Package> packages) {
		packageSeleter.setPackages(packages);

	}

	@Override
	public ArrayList<Package> getPackages() {
		return packageSeleter.getPackages();
	}


	@Override
	public void setRelatedUnit(Unit unit) {
		packageSeleter.setRelatedUnit(unit);
	}



}
