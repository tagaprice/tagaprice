package org.tagaprice.client.features.productmanagement.createProduct.desktopView;

import java.util.ArrayList;
import java.util.List;

import org.tagaprice.client.features.productmanagement.createProduct.ICreateProductView;
import org.tagaprice.client.generics.widgets.CategorySelecter;
import org.tagaprice.client.generics.widgets.StdFrame;
import org.tagaprice.client.generics.widgets.UnitSelecter;
import org.tagaprice.shared.entities.Unit;
import org.tagaprice.shared.entities.categorymanagement.Category;
import org.tagaprice.shared.entities.productmanagement.Package;
import org.tagaprice.shared.entities.searchmanagement.StatisticResult;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class CreateProductViewImpl extends Composite implements ICreateProductView {

	private HorizontalPanel _hoPa1 = new HorizontalPanel();
	private StdFrame _productFrame = new StdFrame();
	private StdFrame _statisticFrame = new StdFrame();
	private TextBox _productName = new TextBox();
	private HorizontalPanel _productHeadPanel = new HorizontalPanel();
	private Label _statisticHead = new Label("Statistic");
	private UnitSelecter _unit = new UnitSelecter();
	private VerticalPanel _productBodyPanel = new VerticalPanel();
	private CategorySelecter _category = new CategorySelecter();
	
	public CreateProductViewImpl() {
		_hoPa1.setWidth("100%");
		
		//Product
		_hoPa1.add(_productFrame);
		
		//product Name
		_productName.setText("Product Name");
		_productHeadPanel.add(_productName);
		_productHeadPanel.setCellWidth(_productName, "100%");
		
		//product unit
		_productHeadPanel.add(_unit);
		_productHeadPanel.setWidth("100%");
		_productFrame.setHeader(_productHeadPanel);
		_hoPa1.setCellWidth(_productFrame, "300px");
		
		//product Body
		_productBodyPanel.setWidth("100%");
		_productFrame.setBody(_productBodyPanel);
		_productBodyPanel.add(_category);
		
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
			
			_mockProperites.setWidget(0, 1, new Label("1490kJ"));
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
		
		{//Nutrition facts
			Label _mockPropertieHead = new Label("Opening Hours");
			_mockPropertieHead.setStyleName("propertyHeader");
			_productBodyPanel.add(_mockPropertieHead);
			
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
			_mockProperites.setWidget(6, 1, new Label("Cloesed"));
			
			
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
			
			_productBodyPanel.add(_mockProperites);
			}
		
		//Statistic
		_hoPa1.add(_statisticFrame);
		_statisticFrame.setHeader(_statisticHead);
		
		
		initWidget(_hoPa1);
	}
	
	@Override
	public String getProductTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUnit(Unit unit) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Unit getUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setCategory(Category category) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Category getCategory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPackage(Package ipackage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPackages(ArrayList<Package> iPackage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<Package> getPackages() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setStatisticResults(List<StatisticResult> results) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPresenter(Presenter presenter) {
		// TODO Auto-generated method stub
		
	}

}
