package org.tagaprice.client.features.shopmanagement.createShop.desktopView;

import java.util.Date;
import java.util.List;
import java.util.Map;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.generics.widgets.AddressSelecter;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.IStatisticChangeHandler;
import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.PropertyDefaultSelecter;
import org.tagaprice.client.generics.widgets.StatisticSelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.IStatisticSelecter.TYPE;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.client.generics.events.CategorySelectedEventHandler;
import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateShopViewImpl extends Composite implements ICreateShopView {

	private Presenter _presenter;
	private StdFrame _stdFrame = new StdFrame();
	private HorizontalPanel _shopHeadPanel = new HorizontalPanel();
	private MorphWidget _shopTitle = new MorphWidget();
	private VerticalPanel _shopBodyPanel = new VerticalPanel();
	private StatisticSelecter _statistic = new StatisticSelecter();
	private VerticalPanel _infoStatVePa = new VerticalPanel();
	private VerticalPanel _infoVePa = new VerticalPanel();
	private VerticalPanel _statisticBodyPanel = new VerticalPanel();
	private AddressSelecter _address = new AddressSelecter();
	private boolean _readonly = true;
	private CategorySelecter _category = new CategorySelecter();
	private PropertyDefaultSelecter _propertyDefaultSelecter = new PropertyDefaultSelecter();
	
	
	public CreateShopViewImpl() {
		
		
		
		//shop name
		_shopTitle.setValue("New Shop");
		_shopTitle.config(false, true);
		_shopHeadPanel.add(_shopTitle);
		
		_shopHeadPanel.setWidth("100%");
		_stdFrame.setHeader(_shopHeadPanel);
		

		
		//shop body
		_shopBodyPanel.setWidth("100%");
		_stdFrame.setBody(_shopBodyPanel,"300px");
		//_stdFrame.setBodyCellWidth(_shopBodyPanel, "300px");
		
		//add Category Selecter
		_category.config(false, false);
		_shopBodyPanel.add(_category);
		_category.addCategorySelectedEventHandler(new CategorySelectedEventHandler() {
			
			@Override
			public void onCategoryClicked(String categoryId) {
				_presenter.onCategoryClicked(categoryId);				
			}
		});
		
		//Address Selecter
		_shopBodyPanel.add(_address);
		
				
		//properties
		Label _mockPropertieHead = new Label("Properties");
		_mockPropertieHead.setStyleName("propertyHeader");
		_shopBodyPanel.add(_mockPropertieHead);
		_shopBodyPanel.add(_propertyDefaultSelecter);
		
		
		
		//Statistic		
		//saveButton
		_stdFrame.addSaveClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				_presenter.onSaveEvent();		
			}
		});
		
		//enable button
		_stdFrame.setButtonsVisible(true);
		
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
	
		//info and Statistic
		_infoStatVePa.setWidth("100%");
		_statisticBodyPanel.setWidth("100%");
		_statisticBodyPanel.add(_infoStatVePa);
		
		//infoPanel
		_infoStatVePa.add(_infoVePa);
		Label stepByStep = new Label("STEP by STEP");
		Label point1 = new Label("1.) Shop title");
		Label point1eg = new Label("eg.: Hofer, Billa, Happy Noodles");
		Label point2 = new Label("2.) Set the category in which you would see this shop/restaurant");
		Label point2eg = new Label("eg.: restaurant --> Fast Food, supermarket");

		stepByStep.setStyleName("createInfo");
		point1.setStyleName("createInfo");
		point1eg.setStyleName("createEgInfo");
		point2.setStyleName("createInfo");
		point2eg.setStyleName("createEgInfo");
		
		_infoVePa.add(stepByStep);
		_infoVePa.add(point1);
		_infoVePa.add(point1eg);
		_infoVePa.add(point2);
		_infoVePa.add(point2eg);
		_infoVePa.setVisible(false);
		
		
		//Statistic Results
		//_statisticBodyPanel.setWidth("100%");
		_statistic.setType(TYPE.SHOP);
		_infoStatVePa.add(_statistic);
		_statistic.setMapVisible(false);
		//_statisticFrame.setBody(_statisticBodyPanel);
		_stdFrame.setBody(_statisticBodyPanel);
		
		_statistic.setDate(new Date(1312192800000L), new Date());
		
		_statistic.addStatisticChangeHandler(new IStatisticChangeHandler() {
			@Override
			public void onChange(BoundingBox bbox, Date begin, Date end) {
				_presenter.onStatisticChangedEvent(bbox, begin, end);
			}
		});
		
		//initWidget(_hoPa1);
		initWidget(_stdFrame);
	}
	
	
	
	@Override
	public String getTitle() {
		return _shopTitle.getValue();
	}

	@Override
	public Address getAddress() {
		return _address.getAddress();
	}

	@Override
	public Shop getBranding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(Address address) {
		Log.debug("setAddres: "+address);
		_address.setAddress(address);
	}

	@Override
	public void setBranding(Shop branding) {
		if(branding!=null)
			_statistic.setMapVisible(true);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBrandingSearchResults(List<Shop> results) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		_presenter=presenter;			
	}

	@Override
	public void setTitle(String title) {
		_shopTitle.setValue(title);		
	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		_statistic.setStatisticResults(results);		
	}
	
	public void setReadOnly(boolean read){
		_readonly=read;
		_shopTitle.setReadOnly(_readonly);
		_address.setReadOnly(_readonly);
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
	public void setCategory(Category category) {
		_category.setCategory(category);
	}

	@Override
	public Category getCategory() {
		return _category.getCategory();
	}



	@Override
	public void setStatisticLatLon(double lat, double lon) {
		_statistic.setLatLon(lat, lon);		
	}



	@Override
	public void setPropertyList(Map<String, Object> propertyList) {
		_propertyDefaultSelecter.setPropertyList(propertyList);	
	}



	@Override
	public void setStatisticIsLoading() {
		_statistic.setLoading();
	}
	
}
