package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;
import org.tagaprice.client.gwt.client.generics.widgets.IPackageSelecter;
import org.tagaprice.client.gwt.client.generics.widgets.IQuantityChangeHandler;
import org.tagaprice.client.gwt.shared.entities.dump.IQuantity;
import org.tagaprice.client.gwt.shared.entities.dump.Quantity;
import org.tagaprice.client.gwt.shared.entities.productmanagement.IPackage;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Package;
import org.tagaprice.core.entities.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class PackageSelecter extends Composite implements IPackageSelecter {

	private VerticalPanel _vePa = new VerticalPanel();
	private VerticalPanel _vePa2 = new VerticalPanel();
	private ArrayList<IPackage> _iPackage = new ArrayList<IPackage>();
	private Unit _relatedUnit;
	private ArrayList<QuantitySelecter> _quantitySaveList = new ArrayList<QuantitySelecter>();
	private QuantitySelecter newQuant = new QuantitySelecter();


	public PackageSelecter() {
		initWidget(_vePa);
		_vePa.add(new Label("Packages"));
		_vePa.add(_vePa2);
		HorizontalPanel hoPaPlus = new HorizontalPanel();

		newQuant.setRelatedUnit(_relatedUnit);
		hoPaPlus.add(newQuant);
		Button addPackage = new Button("+");
		hoPaPlus.add(addPackage);

		addPackage.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				//addPackage(new Package(newQuant.getQuantity()));
				Package save = new Package(new Quantity(newQuant.getQuantity().getQuantity(), newQuant.getQuantity().getUnit()));
				addPackage(save);
				_iPackage.add(save);
				newQuant.setQuantity(new Quantity(0.0, _relatedUnit));
			}
		});

		_vePa.add(hoPaPlus);
	}

	private void addPackage(final IPackage iPackage){
		QuantitySelecter temp = new QuantitySelecter();
		temp.setRelatedUnit(_relatedUnit);
		temp.setQuantity(iPackage.getQuantity());

		temp.addQuantityChangeHandler(new IQuantityChangeHandler() {

			@Override
			public void onChange(IQuantity quantity) {
				iPackage.setQuantity(quantity);

			}
		});

		_vePa2.add(temp);
		_quantitySaveList.add(temp);
	}

	@Override
	public void setRelatedUnit(Unit unit){
		_relatedUnit=unit;
		newQuant.setRelatedUnit(_relatedUnit);
	}

	@Override
	public void setPackages(ArrayList<IPackage> iPackage) {
		_vePa2.clear();
		_iPackage.clear();
		_quantitySaveList.clear();
		_iPackage.addAll(iPackage);


		for(IPackage p:_iPackage){
			addPackage(p);
		}

	}

	@Override
	public ArrayList<IPackage> getPackages() {
		ArrayList<IPackage> copyList = new ArrayList<IPackage>();


		copyList.addAll(_iPackage);
		return copyList;
	}


}
