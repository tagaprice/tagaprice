package org.tagaprice.client.gwt.shared.entities.shopmanagement;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.tagaprice.client.gwt.shared.entities.IRevisionId;
import org.tagaprice.client.gwt.shared.entities.RevisionId;
import org.tagaprice.client.gwt.shared.entities.productmanagement.Country;

public class AddressTest extends TestCase {

	String _street = "street1";
	String _zip = "zip1";
	String _city = "city1";
	Country _country = Country.at;
	double _lat = 12.34;
	double _lng = 56.78;
	ISubsidiary testAddress;
	IRevisionId _revisionId = new RevisionId(1L, 2);

	@Override
	@Before
	public void setUp() throws Exception {
		testAddress = new Subsidiary(_revisionId, _street,_zip,_city,_country,_lat,_lng);
	}



	public void testUpdateAddress(){
		testAddress.setCity(_city+"2");
		testAddress.setCountry(Country.de);
		testAddress.setZip(_zip+"2");
		testAddress.setStreet(_street+"2");
		testAddress.setLat(_lat+2);
		testAddress.setLng(_lng+2);
		testAddress.setRevisionId(new RevisionId(_revisionId.getId()+2, _revisionId.getRevision()+2));

		assertEquals(testAddress.getCity(), _city+"2");
		assertEquals(testAddress.getCountry(), Country.de);
		assertEquals(testAddress.getZip(), _zip+"2");
		assertEquals(testAddress.getStreet(), _street+"2");
		assertEquals(testAddress.getLat(), _lat+2);
		assertEquals(testAddress.getLng(), _lng+2);
		assertEquals(testAddress.getRevisionID(), new RevisionId(_revisionId.getId()+2, _revisionId.getRevision()+2));
		assertEquals(testAddress.getRevisionID().getId(), _revisionId.getId()+2);
		assertEquals(testAddress.getRevisionID().getRevision(), _revisionId.getRevision()+2);
	}

	@Test
	public void testCity() {
		assertEquals(testAddress.getCity(), _city);
	}

	@Test
	public void testCountry() {
		assertEquals(testAddress.getCountry(), _country);
	}

	@Test
	public void testLat() {
		assertEquals(testAddress.getLat(), _lat);
	}

	@Test
	public void testLng() {
		assertEquals(testAddress.getLng(), _lng);
	}

	@Test
	public void testStreet() {
		assertEquals(testAddress.getStreet(), _street);
	}

	@Test
	public void testZip() {
		assertEquals(testAddress.getZip(), _zip);
	}


	@Test
	public void testRevisionID() {
		assertEquals(testAddress.getRevisionID(), _revisionId);
	}


}
