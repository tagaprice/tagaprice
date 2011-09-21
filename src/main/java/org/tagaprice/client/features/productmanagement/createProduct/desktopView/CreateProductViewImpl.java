package org.tagaprice.client.features.productmanagement.createProduct.desktopView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.generics.events.CategorySelectedEventHandler;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.IStatisticChangeHandler;
import org.tagaprice.client.generics.widgets.IUnitChangedHandler;
import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.PackageSelecter;
import org.tagaprice.client.generics.widgets.StatisticSelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.client.generics.widgets.IStatisticSelecter.TYPE;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateProductViewImpl extends Composite implements ICreateProductView {
	private Presenter _presenter;
	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private StdFrame _stdFrame = new StdFrame();
	private MorphWidget _productTitle = new MorphWidget();
	private HorizontalPanel _productHeadPanel = new HorizontalPanel();
	private VerticalPanel _infoStatVePa = new VerticalPanel();
	private VerticalPanel _infoVePa = new VerticalPanel();
	private VerticalPanel _statisticBodyPanel = new VerticalPanel();
	private UnitSelecter _unit = new UnitSelecter();
	private VerticalPanel _productBodyPanel = new VerticalPanel();
	private CategorySelecter _category = new CategorySelecter();
	private StatisticSelecter _statistic = new StatisticSelecter();
	private PackageSelecter _packages = new PackageSelecter();
	private boolean _readonly = true;
	
	
	//This is a mock. We must replace it later
	private MorphWidget _brenn = new MorphWidget();
	private UnitSelecter _brennUnit = new UnitSelecter();
	
	public CreateProductViewImpl() {
		_hoPa1.setWidth("100%");
		
		//Product		
		
		//product Name
		_productTitle.config(false, true);
		_productTitle.setValue("New Product");
		_productHeadPanel.add(_productTitle);
		_productHeadPanel.setCellWidth(_productTitle, "100%");
		
		// [
		//_productHeadPanel.add(new Label("["));
		
		//product unit
		_unit.config(true);
		_productHeadPanel.add(_unit);
		_productHeadPanel.setWidth("100%");
		_stdFrame.setHeader(_productHeadPanel);
		_unit.addUnitChangedHandler(new IUnitChangedHandler() {

			@Override
			public void onChange(Unit unit) {
				_presenter.onUnitSelectedEvent();
				_packages.setRelatedUnit(_unit.getUnit());

			}
		});
		//_productHeadPanel.add(new Label("]"));
		
		//product Body
		_productBodyPanel.setWidth("100%");
		_stdFrame.setBody(_productBodyPanel, "300px");
		_productBodyPanel.add(_category);
		_category.addCategorySelectedEventHandler(new CategorySelectedEventHandler() {
			
			@Override
			public void onCategoryClicked(String categoryId) {
				_presenter.onCategoryClicked(categoryId);				
			}
		});
		
		
		
		//package sizes
		Label _packageHead = new Label("Package sizes");
		_packageHead.setStyleName("propertyHeader");
		_productBodyPanel.add(_packageHead);
		_productBodyPanel.add(_packages);
		
		//TODO impel properites
		
		
		
		
		//Save Buttons
		
		//enable button
		_stdFrame.setButtonsVisible(true);
		
		//saveButton
		_stdFrame.addSaveClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent();		
			}
		});
		
		//cancel button
		_stdFrame.addCancleClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onCanceEvent();
			}
		});
		
		
		
		
		
		//editButton
		_stdFrame.addEditClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setReadOnly(false);
			}
		});
				
		
		//info and statistic
		_infoStatVePa.setWidth("100%");
		_statisticBodyPanel.setWidth("100%");
		_statisticBodyPanel.add(_infoStatVePa);
		
		
		//infoPanel
		_infoStatVePa.add(_infoVePa);
		Label stepByStep = new Label("STEP by STEP");
		Label point1 = new Label("1.) Product title");
		Label point1eg = new Label("eg.: Coca Cola Light, Billa Frische Bergabuern Heimilch 3,6%");
		Label point2 = new Label("2.) Set the unit in which you buy the product");
		Label point2eg = new Label("eg.: Milk = l (liter), Bread = kg (kilogram)");
		Label point3 = new Label("3.) Set the category in which you would see this product");
		Label point3eg = new Label("eg.: food, beverages, cars");

		stepByStep.setStyleName("createInfo");
		point1.setStyleName("createInfo");
		point1eg.setStyleName("createEgInfo");
		point2.setStyleName("createInfo");
		point2eg.setStyleName("createEgInfo");
		point3.setStyleName("createInfo");
		point3eg.setStyleName("createEgInfo");
		
		_infoVePa.add(stepByStep);
		_infoVePa.add(point1);
		_infoVePa.add(point1eg);
		_infoVePa.add(point2);
		_infoVePa.add(point2eg);
		_infoVePa.add(point3);
		_infoVePa.add(point3eg);
		_infoVePa.setVisible(false);
		
		
		
		//Statistic Results
		//_statisticBodyPanel.setWidth("100%");
		_statistic.setType(TYPE.PRODUCT);
		_infoStatVePa.add(_statistic);

		_stdFrame.setBody(_statisticBodyPanel);
		
		_statistic.setDate(new Date(1312192800000L), new Date());
		_statistic.addStatisticChangeHandler(new IStatisticChangeHandler() {
			@Override
			public void onChange(BoundingBox bbox, Date begin, Date end) {
				_presenter.onStatisticChangedEvent(bbox, begin, end);
			}
		});
				
		
		
		initWidget(_stdFrame);
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
	
	
	public void setReadOnly(boolean read){
		_readonly=read;
		_productTitle.setReadOnly(_readonly);
		_brenn.setReadOnly(_readonly);
		_unit.setReadOnly(_readonly);
		_brennUnit.setReadOnly(_readonly);
		_category.setReadOnly(_readonly);		
		_stdFrame.setReadOnly(_readonly);

		_infoVePa.setVisible(!read);
		_statistic.setVisible(read);
	}

	@Override
	public BoundingBox getStatisticBoundingBox() {
		return _statistic.getBoundingBox();
	}

	@Override
	public Date getStatisticBeginDate() {
		return _statistic.getBeginDate();
	}

	@Override
	public Date getStatisticEndDate() {
		return _statistic.getEndDate();
	}

	@Override
	public void setStatisticLatLon(double lat, double lon) {
		_statistic.setLatLon(lat, lon);		
	}

	@Override
	public void setStatisticIsLoading() {
		_statistic.setLoading();		
	}

}
