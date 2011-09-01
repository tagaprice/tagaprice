package org.tagaprice.client.generics.widgets.desktopView;

import java.util.ArrayList;

import org.tagaprice.client.generics.widgets.IPackageSelecter;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.productmanagement.Package;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PackageSelecter extends Composite implements IPackageSelecter {

	private VerticalPanel _vePa = new VerticalPanel();
	private ArrayList<Package> _iPackage = new ArrayList<Package>();
	private Unit _relatedUnit;
	//private ArrayList<QuantitySelecter> _quantitySaveList = new ArrayList<QuantitySelecter>();
	//private QuantitySelecter newQuant = new QuantitySelecter();


	public PackageSelecter() {
		initWidget(_vePa);


	}

	private void addPackage(final Package iPackage){
		Label tempPackage = new Label(""+iPackage.getQuantity().getQuantity()+" "+iPackage.getQuantity().getUnit().getTitle());
		
		_vePa.add(tempPackage);
	}

	@Override
	public void setRelatedUnit(Unit unit){
		//_relatedUnit=unit;
		//newQuant.setRelatedUnit(_relatedUnit);
	}

	@Override
	public void setPackages(ArrayList<Package> packages) {
		_iPackage.clear();
		_iPackage.addAll(packages);
		_vePa.clear();

		for (Package p: _iPackage) {
			addPackage(p);
		}

	}

	@Override
	public ArrayList<Package> getPackages() {
		ArrayList<Package> copyList = new ArrayList<Package>();


		copyList.addAll(_iPackage);
		return copyList;
	}



}
