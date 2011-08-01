package org.tagaprice.client.features.shopmanagement.createShop.desktopView;

import java.util.Date;
import java.util.List;

import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView.Presenter;
import org.tagaprice.client.features.shopmanagement.createShop.ICreateShopView;
import org.tagaprice.client.generics.widgets.AddressSelecter;
import org.tagaprice.client.generics.widgets.IStatisticChangeHandler;
import org.tagaprice.client.generics.widgets.StatisticSelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.IStatisticSelecter.TYPE;
import org.tagaprice.shared.entities.Address;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;
import org.tagaprice.shared.entities.shopmanagement.Shop;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateShopViewImpl extends Composite implements ICreateShopView {

	private Presenter _presenter;
	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private StdFrame _shopFrame = new StdFrame();
	private StdFrame _statisticFrame = new StdFrame();
	private TextBox _shopTitle = new TextBox();
	private VerticalPanel _shopBodyPanel = new VerticalPanel();
	private StatisticSelecter _statistic = new StatisticSelecter();
	private VerticalPanel _statisticBodyPanel = new VerticalPanel();
	private Label _statisticHead = new Label("Statistic");
	private AddressSelecter _address = new AddressSelecter();
	
	public CreateShopViewImpl() {
		_hoPa1.setWidth("100%");
		
		//Shop
		_hoPa1.add(_shopFrame);
		
		//shop name
		_shopTitle.setText("New Shop");
		_shopFrame.setHeader(_shopTitle);
		
		//Shop Size
		_hoPa1.setCellWidth(_shopFrame, "300px");
		
		
		//shop body
		_shopBodyPanel.setWidth("100%");
		_shopFrame.setBody(_shopBodyPanel);
		
		
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
		_hoPa1.add(_statisticFrame);
		_statisticFrame.setHeader(_statisticHead);
		
		//Statistic Results
		_statisticBodyPanel.setWidth("100%");
		_statistic.setType(TYPE.SHOP);
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
	public String getShopTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Address getAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Shop getBranding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAddress(Address address) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setBranding(Shop branding) {
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
	public void setShopTitle(String title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		_statistic.setStatisticResults(results);		
	}

}
