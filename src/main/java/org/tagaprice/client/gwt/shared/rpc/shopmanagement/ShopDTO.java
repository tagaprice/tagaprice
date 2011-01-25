package org.tagaprice.client.gwt.shared.rpc.shopmanagement;

import java.io.Serializable;
import java.util.ArrayList;

import org.tagaprice.client.gwt.shared.entities.receiptManagement.IReceiptEntry;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;

public class ShopDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7605425037925246259L;
	private IShop _shop;
	private ArrayList<IReceiptEntry> _receiptEntries;

	public ShopDTO() {}
	public ShopDTO(IShop shop, ArrayList<IReceiptEntry> receiptEntries){
		this._shop = shop;
		this._receiptEntries = receiptEntries;
	}
	public IShop getShop() {
		return _shop;
	}
	public ArrayList<IReceiptEntry> getReceiptEntries() {
		return _receiptEntries;
	}


}
