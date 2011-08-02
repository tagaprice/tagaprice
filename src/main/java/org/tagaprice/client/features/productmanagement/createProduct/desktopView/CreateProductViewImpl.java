package org.tagaprice.client.features.productmanagement.createProduct.desktopView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView.Presenter;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.IStatisticChangeHandler;
import org.tagaprice.client.generics.widgets.IUnitChangedHandler;
import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.PackageSelecter;
import org.tagaprice.client.generics.widgets.StatisticSelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.client.generics.widgets.IMorphWidget.Type;
import org.tagaprice.client.generics.widgets.IStatisticSelecter.TYPE;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateProductViewImpl extends Composite implements ICreateProductView {
	private Presenter _presenter;
	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private StdFrame _productFrame = new StdFrame();
	private StdFrame _statisticFrame = new StdFrame();
	private MorphWidget _productTitle = new MorphWidget();
	private HorizontalPanel _productHeadPanel = new HorizontalPanel();
	private Label _statisticHead = new Label("Statistic");
	private VerticalPanel _statisticBodyPanel = new VerticalPanel();
	private UnitSelecter _unit = new UnitSelecter();
	private VerticalPanel _productBodyPanel = new VerticalPanel();
	private CategorySelecter _category = new CategorySelecter();
	private StatisticSelecter _statistic = new StatisticSelecter();
	private PackageSelecter _packages = new PackageSelecter();
	private boolean _readonly = true;
	
	//This is a mock. We must replace it later
	private Image _editImage = new Image("desktopView/187-pencil_n.png");
	MorphWidget _brenn = new MorphWidget();
	
	public CreateProductViewImpl() {
		_hoPa1.setWidth("100%");
		
		//Product
		_hoPa1.add(_productFrame);
		
		//Edit button
		_editImage.setSize("18px", "18px");
		_productHeadPanel.add(_editImage);
		_editImage.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setReadOnly(!_readonly);
			}
		});
		
		
		//product Name
		_productTitle.configMorph(Type.STRING, true, "Coca Cola Light", false, true);
		_productTitle.setValue("New Product");
		_productHeadPanel.add(_productTitle);
		_productHeadPanel.setCellWidth(_productTitle, "100%");
		
		//product unit
		_productHeadPanel.add(_unit);
		_productHeadPanel.setWidth("100%");
		_productFrame.setHeader(_productHeadPanel);
		_hoPa1.setCellWidth(_productFrame, "300px");
		_unit.addUnitChangedHandler(new IUnitChangedHandler() {

			@Override
			public void onChange(Unit unit) {
				_presenter.onUnitSelectedEvent();
				_packages.setRelatedUnit(_unit.getUnit());

			}
		});
		
		//product Body
		_productBodyPanel.setWidth("100%");
		_productFrame.setBody(_productBodyPanel);
		_productBodyPanel.add(_category);
		
		
		//package sizes
		Label _packageHead = new Label("Package sizes");
		_packageHead.setStyleName("propertyHeader");
		_productBodyPanel.add(_packageHead);
		_productBodyPanel.add(_packages);
		
		
		//TODO implement Properties 
		//Mock properties
		
		{//Nutrition facts
			Label _mockPropertieHead = new Label("Nutrition Facts");
			_mockPropertieHead.setStyleName("propertyHeader");
			_productBodyPanel.add(_mockPropertieHead);
			
			Grid _mockProperites = new Grid(6,2);
			_mockProperites.setStyleName("propertyGrid");
			_mockProperites.setWidth("100%");
			_mockProperites.setWidget(0, 0, new Label("Brennwert"));
			_mockProperites.setWidget(1, 0, new Label("Eiweiss"));
			_mockProperites.setWidget(2, 0, new Label("Kohlenhydrate"));
			_mockProperites.setWidget(3, 0, new Label("Fett"));
			_mockProperites.setWidget(4, 0, new Label("Ballaststoffe"));
			_mockProperites.setWidget(5, 0, new Label("Natrium"));
			
			_brenn.configMorph(Type.STRING, false, "", true, false);
			_brenn.setValue("1490kJ");
			_mockProperites.setWidget(0, 1, _brenn);
			_mockProperites.setWidget(1, 1, new Label("12,4g"));
			_mockProperites.setWidget(2, 1, new Label("72,2g"));
			_mockProperites.setWidget(3, 1, new Label("1,4g"));
			_mockProperites.setWidget(4, 1, new Label("3,6g"));
			_mockProperites.setWidget(5, 1, new Label("0,7g"));
			
			
			//style name
			_mockProperites.getCellFormatter().setStyleName(0, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(1, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(2, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(3, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(4, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(5, 0, "namecell");
			
			
			//stlye value
			_mockProperites.getCellFormatter().setStyleName(0, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(1, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(2, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(3, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(4, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(5, 1, "valuecell");
			
			_productBodyPanel.add(_mockProperites);
		}
		
		
		
		
		//Save button
		//TODO show save only if something has changed
		//TODO create abort button
		Button _saveButton = new Button("save");
		_saveButton.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent();				
			}
		});
		_productBodyPanel.add(_saveButton);
		
		
		//Statistic
		_hoPa1.add(_statisticFrame);
		_statisticFrame.setHeader(_statisticHead);
		
		//Statistic Results
		_statisticBodyPanel.setWidth("100%");
		_statistic.setType(TYPE.PRODUCT);
		_statisticBodyPanel.add(_statistic);
		_statisticFrame.setBody(_statisticBodyPanel);
		
		_statistic.addStatisticChangeHandler(new IStatisticChangeHandler() {
			@Override
			public void onChange(BoundingBox bbox, Date begin, Date end) {
				_presenter.onStatisticChangedEvent(bbox, begin, end);
			}
		});
				
		
		
		initWidget(_hoPa1);
	}
	
	@Override
	public void setTitle(String title) {
		_productTitle.setValue(title);
	}
	
	@Override
	public String getTitle() {
		return _productTitle.getValue();
	}

	@Override
	public void setUnit(Unit unit) {
		_unit.setUnit(unit);		
		_packages.setRelatedUnit(unit);
	}

	@Override
	public Unit getUnit() {
		return _unit.getUnit();
	}

	@Override
	public void setCategory(Category category) {
		_category.setCategory(category);
	}

	@Override
	public Category getCategory() {
		return _category.getCategory();
	}

	@Override
	public void addPackage(Package ipackage) {
		_packages.getPackages().add(ipackage);
		_packages.setPackages(_packages.getPackages());
	}

	@Override
	public void setPackages(ArrayList<Package> iPackage) {
		_packages.setPackages(iPackage);		
	}

	@Override
	public ArrayList<Package> getPackages() {
		return _packages.getPackages();
	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		_statistic.setStatisticResults(results);		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;		
	}
	
	
	private void setReadOnly(boolean read){
		_readonly=read;
		_productTitle.setReadOnly(_readonly);
		_brenn.setReadOnly(_readonly);
	}

}