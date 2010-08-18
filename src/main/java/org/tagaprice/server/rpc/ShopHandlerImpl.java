package org.tagaprice.server.rpc;

import org.tagaprice.shared.Address;
import org.tagaprice.shared.PropertyData;
import org.tagaprice.shared.PropertyValidator;
import org.tagaprice.shared.SearchResult;
import org.tagaprice.shared.ShopData;
import org.tagaprice.shared.rpc.ShopHandler;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ShopHandlerImpl extends RemoteServiceServlet implements ShopHandler{

	
	@Override
	public ShopData get(Long id) throws IllegalArgumentException {
		// TODO Auto-generated method stub
		
		ShopData shop = new ShopData(123, 2, "ACME Store", 3, 30l, null, new Address("Park Avenue 23", "New York", "USA"));
		SearchResult<PropertyData> propList = new SearchResult<PropertyData>();
		propList.add(new PropertyData("type", "Type", "drugstore", null));
		shop.setProperties(propList);
		
		return shop;
	}

	@Override
	public ShopData save(ShopData data) throws IllegalArgumentException {
		// TODO Auto-generated method stub		
		TypeHandlerImpl th = new TypeHandlerImpl();
		
		if(PropertyValidator.isValid(th.get(data.getTypeId()), data.getProperties())){
			System.out.println("save VALID");
		}else{
			System.out.println("save InVALID");
		}
		
		
		if(data.getId()==0){
			System.out.println("new");
		}else{
			//SAVE	
		}
		return data;
	}

}
