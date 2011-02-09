package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.widgets.IPackageSelecter;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;
import org.tagaprice.core.entities.Unit;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PackageSelecter extends Composite implements IPackageSelecter {

	private VerticalPanel _vePa = new VerticalPanel();
	private ArrayList<IPackage> _iPackage = new ArrayList<IPackage>();
	private Unit _relatedUnit;

	public PackageSelecter() {
		initWidget(_vePa);
	}

	@Override
	public void setRelatedUnit(Unit unit){
		_relatedUnit=unit;
	}

	@Override
	public void setPackages(ArrayList<IPackage> iPackage) {
		_vePa.clear();
		_iPackage.clear();
		_iPackage.addAll(iPackage);

		_vePa.add(new Label("Packages"));

		for(IPackage p:_iPackage){
			QuantitySelecter temp = new QuantitySelecter();
			temp.setRelatedUnit(_relatedUnit);
			temp.setQuantity(p.getQuantity());
			_vePa.add(temp);
		}

	}

	@Override
	public ArrayList<IPackage> getPackages() {
		return _iPackage;
	}


}
