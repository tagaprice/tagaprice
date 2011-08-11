package org.tagaprice.client.desktopView;

import java.util.List;

import org.tagaprice.client.ClientFactory;
import org.tagaprice.client.IUi;
import org.tagaprice.client.features.accountmanagement.login.LoginPresenter;
import org.tagaprice.client.generics.events.LoginChangeEvent;
import org.tagaprice.client.generics.events.LoginChangeEventHandler;
import org.tagaprice.client.generics.widgets.InfoBox;
import org.tagaprice.client.generics.widgets.desktopView.PackagePreview;
import org.tagaprice.client.generics.widgets.desktopView.ShopPreview;
import org.tagaprice.shared.entities.BoundingBox;
import org.tagaprice.shared.entities.productmanagement.Product;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.exceptions.dao.DaoException;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.ActivityManager;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

public class UIDesktop implements IUi {

	private VerticalPanel vePa1 = new VerticalPanel();
	private HorizontalPanel menu = new HorizontalPanel();
	private SimplePanel center = new SimplePanel();
	private HorizontalPanel bottom = new HorizontalPanel();
	private InfoBox _infoBox = new InfoBox();
	private TextBox search = new TextBox();
	private PopupPanel _infoBoxPopUp = new PopupPanel();
	private ActivityManager _activityManager;
	private ClientFactory _clientFactory;
	private PopupPanel loginPop = new PopupPanel(true);
	
	private PopupPanel _searchPopup = new PopupPanel(true);
	private VerticalPanel _shopProductSearchPanel = new VerticalPanel();
	private VerticalPanel _shopSearchPanel = new VerticalPanel();
	private VerticalPanel _productSearchPanel = new VerticalPanel();
	private int _productSearchCount=0;
	private int _shopSearchCount=0;

	private void init(){
		{
		vePa1.setWidth("100%");
		
		//infobox
		//Add InfoBox Popup
		_infoBoxPopUp.setWidget(_infoBox);
		_infoBoxPopUp.show();
		


		//INfo test
		//TODO Find out why setWidth(100%) is not working
		_infoBox.setWidth((Window.getClientWidth()-20)+"px");
		
		
		
		
		//menu
		//menu.setSize("100%", "30px");
		menu.setStyleName("header");
		vePa1.add(menu);
		
		//search
		SimplePanel nothing = new SimplePanel();
		menu.add(nothing);
		menu.setCellWidth(nothing, "50%");
		
		search.setText("");
		menu.add(search);
		menu.setCellHorizontalAlignment(search, HorizontalPanel.ALIGN_CENTER);
		
		
		_shopProductSearchPanel.setStyleName("popBackground");
		_shopProductSearchPanel.setWidth("630px");
		
		_productSearchPanel.setWidth("100%");
		_shopProductSearchPanel.add(_productSearchPanel);
		_shopSearchPanel.setWidth("100%");
		_shopProductSearchPanel.add(_shopSearchPanel);
		
		
		_searchPopup.getElement().getStyle().setZIndex(2000);
		_searchPopup.setWidget(_shopProductSearchPanel);
		
		//Implement Searching
		search.addKeyUpHandler(new KeyUpHandler() {
			
			@Override
			public void onKeyUp(KeyUpEvent arg0) {
				search(search.getText());
			}
		});
		
		
		
		
		
		//position
		Image position = new Image("desktopView/location.png");
		position.setStyleName("location");
		menu.add(position);
		
		//login
		final Label login = new Label("Sign in");
		login.setStyleName("login");
		//Button login = new Button("Sign in");
		//$(login).as(gwtquery.plugins.ui.Ui.Ui).button(gwtquery.plugins.ui.widgets.Button.Options.create().icons(gwtquery.plugins.ui.widgets.Button.Icons.create().secondary("ui-icon-triangle-1-s"))); //
		menu.add(login);
		menu.setCellHorizontalAlignment(login, HorizontalPanel.ALIGN_RIGHT);
		menu.setCellWidth(login, "50%");
		
		
		loginPop.getElement().getStyle().setZIndex(2000);
		login.addClickHandler(new ClickHandler() {
			
			@Override
			public void onClick(ClickEvent arg0) {
				LoginPresenter loginPres = new LoginPresenter(_clientFactory);
				loginPop.setWidget(loginPres.getView());
				loginPop.showRelativeTo(login);				
			}
		});
		
		
		
		
		
		//center
		center.setStyleName("center");
		vePa1.add(center);
		vePa1.setCellHorizontalAlignment(center, VerticalPanel.ALIGN_CENTER);
		
		//center.setHeight((Window.getClientHeight()-240-120)+"px");
		//vePa1.setCellHeight(center, "100%");
		
		
		
		//bottom
		bottom.setStyleName("bottom");
		vePa1.add(bottom);
		
		//bottom Text
		HorizontalPanel bottomText = new HorizontalPanel();
		bottomText.setStyleName("bottom-text");
		HTML lefthtml = new HTML("" +
				"<h2>Licence</h2> " +
				"<a href=\"http://creativecommons.org/licenses/by-sa/3.0/\">Creative Commons Attribution-ShareAlike 3.0 Unported License</a> <br /> " +
				"<a href=\"http://www.gnu.org/licenses/agpl.html\">AGPLv3</a> " +
				"<h2>Blog</h2> " +
				"<a href=\"http://blog.tagaprice.org/\">blog.tagaprice.org</a> " +
				"<h2>Development</h2> " +
				"<a href=\"http://github.com/tagaprice\">api.tagaprice.org</a> <br /> " +
				"<a href=\"http://github.com/tagaprice\">http://github.com/tagaprice</a>");
		HTML righthtml = new HTML("" +
				"<h2>Email</h2> " +
				"team[at]tagaprice[dot]org " +
				"<h2>Twitter</h2> " +
				"<a href=\"http://twitter.com/tagaprice\">@tagaprice</a>");
		bottomText.add(lefthtml);
		bottomText.add(righthtml);
		bottom.add(bottomText);
		bottom.setCellHorizontalAlignment(bottomText, HorizontalPanel.ALIGN_CENTER);
				
		
		//add your stdlinks for debug
		bottomText.add(new HTML(
				"<h2>Debug</h2> " + 
				"<a href=\"#Start:null\">home</a> <br /> "
				+ "<a href=\"#CreateProduct:/create\">New Product</a> <br /> "
				+ "<a href=\"#ListProducts:/show\">List Products</a> <br /> "
				+ "<a href=\"#CreateShop:/create\">New Shop</a> <br /> "
				+ "<a href=\"#ListShops:\">List Shops</a> <br /> "
				+ "<a href=\"#CreateReceipt:/create\">New Receipt</a> <br /> "
				+ "<a href=\"#ListReceipts:/show\">List Receipts</a> <br /> "
				+ "<a href=\"#Register:/REGISTER\">Sign Up</a> <br /> "));
	}
		
		
	
		//Set Popvisilb
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {
			@Override
			public void onLoginChange(LoginChangeEvent event) {
				loginPop.hide();
			}
		});


		_activityManager.setDisplay(center);


		


		//User loggedInHandler
		_clientFactory.getEventBus().addHandler(LoginChangeEvent.TYPE, new LoginChangeEventHandler() {

			@Override
			public void onLoginChange(LoginChangeEvent event) {
				//TODO Set SignIn invisible and add User plus name and so on.
				/*
				if(event.isLoggedIn()){
					login.setVisible(false);
					register.setVisible(false);
					logout.setVisible(true);
				}else{
					login.setVisible(true);
					register.setVisible(true);
					logout.setVisible(false);
				}*/

			}
		});
	}


	@Override
	public void initUI(ActivityManager activityManager, ClientFactory clientFactory) {
		_activityManager=activityManager;
		_clientFactory=clientFactory;
		init();

		RootPanel.get().add(vePa1);

	}


	@Override
	public InfoBox getInfoBox() {
		return _infoBox;
	}
	
	private void search(String searchCritera){
		_productSearchCount++;
		_shopSearchCount++;
		
		final int curProductSearchCount=_productSearchCount;
		final int curShopSearchCount=_shopSearchCount;
		
		_clientFactory.getSearchService().searchShop(searchCritera, 
				new BoundingBox(90.0, 90.0, -90.0, -90.0), 
				new AsyncCallback<List<Shop>>() {
			
			@Override
			public void onSuccess(List<Shop> response) {
				if(curShopSearchCount==_shopSearchCount){
					Log.debug("ShopSearch successfull: count: "+response.size());
					_shopSearchPanel.clear();
					
					for(final Shop s:response){
						ShopPreview dumpShop = new ShopPreview(s);
						dumpShop.addClickHandler(new ClickHandler() {
							
							@Override
							public void onClick(ClickEvent arg0) {
								History.newItem("CreateShop:/show/id/"+s.getId());
								_searchPopup.hide();
								search.setText("");
							}
						});
						_shopSearchPanel.add(dumpShop);
					}	
					
					if(response.size()>0)
						_searchPopup.showRelativeTo(search);
				
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				try{
					throw caught;
				}catch (DaoException e){
					Log.warn(e.getMessage());
				}catch (Throwable e){
					Log.error(e.getMessage());
				}				
			}
		});
		
		_clientFactory.getSearchService().searchProduct(searchCritera, 
				null, 
				new AsyncCallback<List<Product>>() {
					
					@Override
					public void onSuccess(List<Product> response) {
						if(curProductSearchCount==_productSearchCount){
						
							Log.debug("ShopSearch successfull: count: "+response.size());
							_productSearchPanel.clear();
							
							for(final Product pr:response){
								PackagePreview packDump = new PackagePreview(pr, null);
								packDump.addClickHandler(new ClickHandler() {
									
									@Override
									public void onClick(ClickEvent arg0) {
										History.newItem("CreateProduct:/show/id/"+pr.getId());	
										_searchPopup.hide();
										search.setText("");
									}
								});
								_productSearchPanel.add(packDump);
							}
							
							if(response.size()>0)
								_searchPopup.showRelativeTo(search);
						
						}
					}
					
					@Override
					public void onFailure(Throwable caught) {
						try{
							throw caught;
						}catch (DaoException e){
							Log.warn(e.getMessage());
						}catch (Throwable e){
							Log.error(e.getMessage());
						}				
					}
				});
	}
}
