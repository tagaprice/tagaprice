package org.tagaprice.client.features.shopmanagement.createShop.desktopView;

import java.util.Date;
import java.util.List;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.generics.widgets.AddressSelecter;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.IStatisticChangeHandler;
import org.tagaprice.client.generics.widgets.MorphWidget;
import org.tagaprice.client.generics.widgets.StatisticSelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.IStatisticSelecter.TYPE;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.client.generics.widgets.IMorphWidget.Type;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateShopViewImpl extends Composite implements ICreateShopView {

	private Presenter _presenter;
	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private StdFrame _stdFrame = new StdFrame();
	private HorizontalPanel _shopHeadPanel = new HorizontalPanel();
	private MorphWidget _shopTitle = new MorphWidget();
	private VerticalPanel _shopBodyPanel = new VerticalPanel();
	private StatisticSelecter _statistic = new StatisticSelecter();
	private VerticalPanel _statisticBodyPanel = new VerticalPanel();
	private Label _statisticHead = new Label("Statistic");
	private AddressSelecter _address = new AddressSelecter();
	private boolean _readonly = true;
	private CategorySelecter _category = new CategorySelecter();
	
	
	
	public CreateShopViewImpl() {
		_hoPa1.setWidth("100%");
		
		
		
		//shop name
		_shopTitle.setValue("New Shop");
		_shopTitle.config(Type.STRING, true, "Billa - Blumenauergasse 1B", false, true);
		_shopHeadPanel.add(_shopTitle);
		
		_shopHeadPanel.setWidth("100%");
		_stdFrame.setHeader(_shopHeadPanel);
		

		
		//shop body
		_shopBodyPanel.setWidth("100%");
		_stdFrame.setBody(_shopBodyPanel,"300px");
		//_stdFrame.setBodyCellWidth(_shopBodyPanel, "300px");
		
		//add Category Selecter
		_category.setCategoryTypeIsProduct(false);
		_shopBodyPanel.add(_category);
		
		//Address Selecter
		_shopBodyPanel.add(_address);
		
		
		
		//TODO implement Properties 
		//Mock properties
		
		{//Openeing Hours
			Label _mockPropertieHead = new Label("Opening Hours");
			_mockPropertieHead.setStyleName("propertyHeader");
			_shopBodyPanel.add(_mockPropertieHead);
			
			Grid _mockProperites = new Grid(7,2);
			_mockProperites.setStyleName("propertyGrid");
			_mockProperites.setWidth("100%");
			_mockProperites.setWidget(0, 0, new Label("Moday"));
			_mockProperites.setWidget(1, 0, new Label("Thuesday"));
			_mockProperites.setWidget(2, 0, new Label("Wednesday"));
			_mockProperites.setWidget(3, 0, new Label("Thirthday"));
			_mockProperites.setWidget(4, 0, new Label("Friday"));
			_mockProperites.setWidget(5, 0, new Label("Saturday"));
			_mockProperites.setWidget(6, 0, new Label("Sunday"));
			
			_mockProperites.setWidget(0, 1, new Label("10h-18h"));
			_mockProperites.setWidget(1, 1, new Label("10h-18h"));
			_mockProperites.setWidget(2, 1, new Label("10h-18h"));
			_mockProperites.setWidget(3, 1, new Label("10h-18h"));
			_mockProperites.setWidget(4, 1, new Label("10h-18h"));
			_mockProperites.setWidget(5, 1, new Label("6h-24h"));
			_mockProperites.setWidget(6, 1, new Label("Closed"));
			
			
			//style name
			_mockProperites.getCellFormatter().setStyleName(0, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(1, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(2, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(3, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(4, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(5, 0, "namecell");
			_mockProperites.getCellFormatter().setStyleName(6, 0, "namecell");
			
			
			//stlye value
			_mockProperites.getCellFormatter().setStyleName(0, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(1, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(2, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(3, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(4, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(5, 1, "valuecell");
			_mockProperites.getCellFormatter().setStyleName(6, 1, "valuecell");
			
			_shopBodyPanel.add(_mockProperites);
		}
		
		
		
		
		
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
				//TODO implement cancel
				setReadOnly(true);
			}
		});
		
		
		
		
		
		//editButton
		_stdFrame.addEditClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				setReadOnly(false);
			}
		});
	
		
		//Statistic Results
		//_statisticBodyPanel.setWidth("100%");
		_statistic.setType(TYPE.SHOP);
		_statisticBodyPanel.add(_statistic);
		_statistic.setMapVisible(false);
		//_statisticFrame.setBody(_statisticBodyPanel);
		_stdFrame.setBody(_statisticBodyPanel);
		
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
	
}
