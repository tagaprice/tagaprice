package org.tagaprice.client.generics.widgets.devView;

import java.util.List;

import org.tagaprice.client.generics.widgets.IUnitChangedHandler;
import org.tagaprice.client.generics.widgets.IUnitSelecter;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.rpc.unitmanagement.IUnitService;
import org.tagaprice.shared.rpc.unitmanagement.IUnitServiceAsync;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UnitSelecter extends Composite implements IUnitSelecter{

	private Button _selectButton = new Button("^");
	private PopupPanel _showUnits = new PopupPanel(true);
	private VerticalPanel _unitList = new VerticalPanel();
	private Unit _cUnit = new Unit();
	private ListBox _listBoxUnit = new ListBox();
	private IUnitChangedHandler _unitChangedHandler;
	private IUnitServiceAsync _unitServiceAsync = GWT.create(IUnitService.class);
	private String _allowId = null;


	public UnitSelecter() {
		//initWidget(_listBoxUnit);
		initWidget(_selectButton);
		allowedUnit(null);

		_selectButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				_showUnits.setWidget(new Label("Loading..."));
				_showUnits.showRelativeTo(_selectButton);
				showFactorizedUnits();

			}
		});


		//add Listener
		_listBoxUnit.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent arg0) {
				if(_unitChangedHandler!=null)_unitChangedHandler.onChange(getUnit());

			}
		});
	}

	@Override
	public void setRelatedUnit(Unit unit) {
		Log.debug("RelativeUnit: "+unit);
		if(unit!=null){
			if(unit.getParent()==null)
				_allowId=unit.getId();
			else
				_allowId=unit.getParentId();
		}
	}

	@Override
	public void setUnit(Unit unit) {
		if(unit!=null){
			Log.debug("setUnit: "+unit.getTitle());
			_selectButton.setText(unit.getTitle()+"^");
			_cUnit=unit;
			int pos = 0;

			/// TODO fix me
			/*		for(int i= 0; i < Unit.values().length; i++ ) {
			if(Unit.values()[i].equals(unit)) {
				pos=i;
			}
		}*/
			_listBoxUnit.setSelectedIndex(pos);
		}else{
			_cUnit=new Unit();
			_selectButton.setText("^");
		}
	}

	@Override
	public Unit getUnit() {
		return _cUnit;
		//return Unit.valueOf(_listBoxUnit.getItemText(_listBoxUnit.getSelectedIndex()));
	}


	/**
	 * This method will set the display part to allowed {@link Unit}s
	 * @param unit allowed unit. If null, all are displayed.
	 */
	private void allowedUnit(Unit unit){


	}

	@Override
	public void addUnitChangedHandler(IUnitChangedHandler unitChangedHandler) {
		_unitChangedHandler=unitChangedHandler;
	}


	private void showFactorizedUnits(){

		_unitServiceAsync.getFactorizedUnits(_allowId, new AsyncCallback<List<Unit>>() {

			@Override
			public void onSuccess(List<Unit> results) {
				Log.debug("get factorizedUnits successfull. count: "+results.size());
				VerticalPanel vePa = new VerticalPanel();



				for(final Unit u:results){
					Label l = new Label(u.getTitle());
					vePa.add(l);

					l.addClickHandler(new ClickHandler() {

						@Override
						public void onClick(ClickEvent arg0) {
							setUnit(u);
							if(_unitChangedHandler!=null)_unitChangedHandler.onChange(getUnit());
						}
					});
				}

				_showUnits.setWidget(vePa);
				_showUnits.showRelativeTo(_selectButton);

			}

			@Override
			public void onFailure(Throwable e) {
				Log.error("showFactorizedUnitsProblem: "+e);
			}
		});


	}
}
