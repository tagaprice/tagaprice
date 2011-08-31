package org.tagaprice.client.generics.widgets.desktopView;

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
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UnitSelecter extends Composite implements IUnitSelecter{

	private Label _selectButton = new Label("Unit");
	private PopupPanel _showUnits = new PopupPanel(true);
	private Unit _cUnit = new Unit();
	private ListBox _listBoxUnit = new ListBox();
	private IUnitChangedHandler _unitChangedHandler;
	private IUnitServiceAsync _unitServiceAsync = GWT.create(IUnitService.class);
	private String _allowId = null;
	private VerticalPanel _unitList = new VerticalPanel();

	private boolean _readonly = true;
	private boolean _isHeadline = false;
	
	public UnitSelecter() {
		
		_selectButton.setStyleName("unitSelecter");
		_showUnits.setStyleName("popBackground");
		
		initWidget(_selectButton);
		setRelatedUnit(null);
		_showUnits.setWidget(_unitList);
		
		_selectButton.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent arg0) {
				if(!_readonly){
					_showUnits.setWidget(new Label("Loading..."));
					_showUnits.showRelativeTo(_selectButton);
					showFactorizedUnits();
				}
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
			_selectButton.setText(unit.getTitle());
			_cUnit=unit;
			int pos = 0;

			_listBoxUnit.setSelectedIndex(pos);
		}else{
			_cUnit=new Unit();
			_selectButton.setText("Unit");
		}
	}

	@Override
	public Unit getUnit() {
		return _cUnit;
		//return Unit.valueOf(_listBoxUnit.getItemText(_listBoxUnit.getSelectedIndex()));
	}


	@Override
	public void addUnitChangedHandler(IUnitChangedHandler unitChangedHandler) {
		_unitChangedHandler=unitChangedHandler;
	}


	private void showFactorizedUnits(){

		if(_unitList.getWidgetCount()>0){
			_showUnits.setWidget(_unitList);
			_showUnits.showRelativeTo(_selectButton);
		}else{
			_unitServiceAsync.getFactorizedUnits(_allowId, new AsyncCallback<List<Unit>>() {
	
				@Override
				public void onSuccess(List<Unit> results) {
	
	
	
					for(final Unit u:results){
						Label l = new Label(u.getTitle());
						_unitList.add(l);
	
						l.addClickHandler(new ClickHandler() {
	
							@Override
							public void onClick(ClickEvent arg0) {
								setUnit(u);
								_showUnits.hide();
								if(_unitChangedHandler!=null)_unitChangedHandler.onChange(getUnit());
							}
						});
					}
	
					_showUnits.setWidget(_unitList);
					_showUnits.showRelativeTo(_selectButton);
	
				}
	
				@Override
				public void onFailure(Throwable e) {
					Log.error("showFactorizedUnitsProblem: "+e);
				}
			});
		}


	}

	@Override
	public void setReadOnly(boolean read) {
		if(read){
			_readonly=true;
			
			if(_isHeadline)_selectButton.setStyleName("unitSelecter headline");
			else _selectButton.setStyleName("unitSelecter");
			
		}else{
			_readonly=false;
			if(_isHeadline)_selectButton.setStyleName("unitSelecter edit headline");
			else _selectButton.setStyleName("unitSelecter edit");
		}
		
	}

	@Override
	public boolean isReadOnly() {
		return _readonly;
	}

	@Override
	public void config(boolean isHeadline) {
		_isHeadline=isHeadline;

		
		setReadOnly(_readonly);
	}
}
