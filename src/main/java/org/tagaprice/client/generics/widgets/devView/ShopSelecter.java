package org.tagaprice.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.generics.widgets.IShopSelecter;
import org.tagaprice.shared.entities.shopmanagement.Shop;
import org.tagaprice.shared.logging.LoggerFactory;
import org.tagaprice.shared.logging.MyLogger;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class ShopSelecter extends Composite implements IShopSelecter {
	MyLogger logger = LoggerFactory.getLogger(ShopSelecter.class);

	ListBox _listBoxShop = new ListBox();
	ArrayList<Shop> _availableShops = new ArrayList<Shop>();

	public ShopSelecter(){
		initWidget(_listBoxShop);
	}

	@Override
	public Shop getShop() {
		if(this._availableShops != null && this._availableShops.size() > 0) {
			return this._availableShops.get(this._listBoxShop.getSelectedIndex());
		} else {
			return null;
		}
	}



	@Override
	public void setShop(Shop shop) {
		logger.log("set shop " + shop);
		if(shop !=null){
			for(int i = 0; i< this._listBoxShop.getItemCount(); i++){
				logger.log("inverstigate" + this._listBoxShop.getValue(i));
				if(this._listBoxShop.getItemText(i).equals(shop.toString())){
					this._listBoxShop.setSelectedIndex(i);
					return;
				}

			}
		}

	}

	@Override
	public void setAvailableShops(ArrayList<Shop> shops) {
		this._availableShops = shops;
		this._listBoxShop.clear();
		for (Shop c : this._availableShops) {
			this._listBoxShop.addItem(c.getTitle(), c.toString());
		}

	}

	@Override
	public HandlerRegistration addChangeHandler(ChangeHandler handler) {
		return _listBoxShop.addChangeHandler(handler);
	}



}
