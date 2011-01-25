package org.tagaprice.client.gwt.server.diplomat.converter;


import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;
import org.tagaprice.client.gwt.shared.entities.shopmanagement.IShop;
import org.tagaprice.core.entities.ReceiptEntry;
import org.tagaprice.core.entities.Shop;


public class ShopConverterTest {

	ShopConverter shopConverter = new ShopConverter();

	IShop newShopGWT;
	Shop newShopCore;
	IShop changedShopGWT;
	Shop changedShopCore;

	String title = "Billa";
	double latitude = 20.2;
	double longitude = 30.4;
	Set<ReceiptEntry> receiptEntries = null;
	Country country = null;




	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		this.newShopCore = new Shop(null, title, latitude, longitude, receiptEntries);
		this.newShopGWT = new org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop(title, "", "", "", country, latitude, longitude);

	}

	@After
	public void tearDown() throws Exception {
	}
	@Test
	public void testConvertNewGWTShopToCore() throws Exception{
		Shop shopCore = shopConverter.convertGWTShoptoCore(this.newShopGWT);

		Assert.assertEquals(this.title, shopCore.getTitle());
		Assert.assertEquals(this.latitude, shopCore.getLatitude(),0.01);
		Assert.assertEquals(this.longitude,shopCore.getLongitude(), 0.01);
		Assert.assertEquals(this.receiptEntries, shopCore.getReceiptEntries());
		Assert.assertEquals(this.newShopCore, shopCore);





	}
}
