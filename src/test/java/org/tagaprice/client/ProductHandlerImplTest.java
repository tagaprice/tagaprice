/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

/**
 * Project: TagAPriceUI
 * Filename: ProductHandlerImplTest.java
 * Date: 01.07.2010
*/
package org.tagaprice.client;
import org.tagaprice.shared.ProductData;
import org.tagaprice.shared.rpc.ProductHandler;
import org.tagaprice.shared.rpc.ProductHandlerAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ProductHandlerImplTest extends GWTTestCase {

	
	
	public void testGet() throws Exception{
		ProductHandlerAsync productHandler = GWT.create(ProductHandler.class);

		
		productHandler.get(152l, new AsyncCallback<ProductData>(){

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				System.out.println("fail");
				assertTrue(false);
				finishTest();
			}

			@Override
			public void onSuccess(ProductData result) {
				// TODO Auto-generated method stub
				assertTrue(result.getId()==152);
				System.out.println("noFail");
				
			}
			
		});
		
		
		delayTestFinish(5000);
	}

	
	
	
	
	@Override
	public String getModuleName() {
		// TODO Auto-generated method stub
		return "org.tagaprice.TagAPriceUI";
	}

}
