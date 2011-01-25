package org.tagaprice.client.gwt.server.diplomat.converter;


import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
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

	long id;
	String title = "Billa";
	double latitude = 20.2;
	double longitude = 30.4;
	Set<ReceiptEntry> receiptEntries = null;
	Country country = Country.at;




	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		this.newShopCore = new Shop(null, title, latitude, longitude, receiptEntries);
		this.newShopGWT = new org.tagaprice.client.gwt.shared.entities.shopmanagement.Shop(new RevisionId(id),title, "", "", "", country, latitude, longitude);

	}

	@After
	public void tearDown() throws Exception {
	}
	/**
	 * Tests if a new shop in GWT is correctly converted to a shop in core
	 * A new shop in core has id= null and in gwt has also id=null
	 * @throws Exception
	 */
	@Test
	public void testConvertNewGWTShopToCore() throws Exception{
		Shop shopCore = shopConverter.convertGWTShoptoCore(this.newShopGWT);

		Assert.assertEquals(null, shopCore.getId());
		Assert.assertEquals(this.title, shopCore.getTitle());
		Assert.assertEquals(this.latitude, shopCore.getLatitude(),0.01);
		Assert.assertEquals(this.longitude,shopCore.getLongitude(), 0.01);
		Assert.assertEquals(this.receiptEntries, shopCore.getReceiptEntries());
		Assert.assertEquals(this.newShopCore, shopCore);

	}
	/**
	 * Tests if a new shop in core is correctly converted to a new shop in gwt
	 * A new shop in core has id= null and in gwt has also id=0L
	 * @throws Exception
	 */

	@Test
	public void testConvertNewCoreShopToGWT() throws Exception{
		IShop shopGWT = shopConverter.convertCoreShopToGWT(this.newShopCore);

		Assert.assertEquals(shopGWT.getRevisionId().getId(), 0L );
		Assert.assertEquals(shopGWT.getTitle(),this.title );
		Assert.assertEquals(shopGWT.getStreet(), "");
		Assert.assertEquals(shopGWT.getZip(), "");
		Assert.assertEquals(shopGWT.getCity(), "");
		Assert.assertEquals(shopGWT.getCountry(), this.country);
		Assert.assertEquals(shopGWT.getLat(), this.latitude, 0.01);
		Assert.assertEquals(shopGWT.getLng(), this.longitude,0.01);
		Assert.assertEquals(this.newShopGWT, shopGWT);

	}
	/**
	 * Tests if a changed shop in core is correctly converted to a  shop in gwt
	 * 
	 * @throws Exception
	 */

	@Test
	public void testConvertChangedGWTShopToCore()throws Exception{


	}

	/**
	 * Tests if a changed shop in core is correctly converted to a  shop in gwt
	 * 
	 * @throws Exception
	 */


	@Test
	public void testConvertChangedCoreShopToGWT()throws Exception{


	}
}
