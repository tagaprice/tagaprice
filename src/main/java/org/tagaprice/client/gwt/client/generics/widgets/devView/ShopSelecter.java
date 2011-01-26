package org.tagaprice.client.gwt.client.generics.widgets.devView;

import java.util.ArrayList;

import org.tagaprice.client.gwt.client.generics.widgets.IShopSelecter;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.client.gwt.shared.logging.LoggerFactory;
import org.tagaprice.client.gwt.shared.logging.MyLogger;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;

public class ShopSelecter extends Composite implements IShopSelecter{
	MyLogger logger = LoggerFactory.getLogger(ShopSelecter.class);

	ListBox _listBoxShop = new ListBox();
	ArrayList<IShop> _availableShops = new ArrayList<IShop>();

	public ShopSelecter(){
		initWidget(_listBoxShop);
	}

	@Override
	public IShop getShop() {
		if(this._availableShops != null && this._availableShops.size() > 0) {
			return this._availableShops.get(this._listBoxShop.getSelectedIndex());
		} else {
			return null;
		}
	}



	@Override
	public void setShop(IShop shop) {
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
	public void setAvailableShops(ArrayList<IShop> shops) {
		this._availableShops = shops;
		this._listBoxShop.clear();
		for (IShop c : this._availableShops) {
			this._listBoxShop.addItem(c.getTitle(), c.toString());
		}

	}



}
