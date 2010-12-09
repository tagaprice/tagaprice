/**
 * 
 */
package org.tagaprice.shared.entities;

import org.junit.*;

/**
 * This is a rather trivial Testcase.
 * @author Martin Weik (afraidoferrors)
 *
 */
public class CountryTest {
	private Country country;

	/**
	 * Small class to test the equals method of Country
	 * @author Martin Weik (afraidoferrors)
	 *
	 */
	public class CountryWithCounty extends Country {

		private static final long serialVersionUID = 1L;
		private String county;
		public CountryWithCounty(String code, String title, String localTitle, String county) {
			super(code, title, localTitle);
			this.county = county;
		}

		@Override
		public boolean equals(Object o) {
			if(o.getClass() == this.getClass()) {
				CountryWithCounty cwc = (CountryWithCounty)o;
				return super.equals(o) && this.county.equals(cwc.county);
			}
			return	false;
		}
	}


	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		country = new Country("de_AT", "Austria", "Österreich");
	}

	/**
	 * Test method for {@link org.tagaprice.shared.entities.Country#getSerializeName()}.
	 */
	@Test
	public void testGetSerializeName() {
		Assert.assertEquals("country", country.getSerializeName());
	}

	/**
	 * Test method for {@link org.tagaprice.shared.entities.Country#getCode()}.
	 */
	@Test
	public void testGetCode() {
		Assert.assertEquals("de_AT", country.getCode());
	}

	/**
	 * Test method for {@link org.tagaprice.shared.entities.Country#getTitle()}.
	 */
	@Test
	public void testGetTitle() {
		Assert.assertEquals("Austria", country.getTitle());
	}

	/**
	 * Test method for {@link org.tagaprice.shared.entities.Country#getLocalTitle()}.
	 */
	@Test
	public void testGetLocalTitle() {
		Assert.assertEquals("Österreich", country.getLocalTitle());
	}

	/**
	 * Test method for {@link org.tagaprice.shared.entities.Country#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObjectStandardCase() {
		//Standard Case... Objects are equal
		Country countryREF = new Country(country.getCode(), country.getTitle(), country.getLocalTitle());
		Assert.assertTrue("equals should return true but returns false", country.equals(countryREF));
		Assert.assertTrue("equals should return true but returns false", countryREF.equals(country));
		//TODO discuss behavior if some fields are null equally
	}

	/**
	 * Test method for {@link org.tagaprice.shared.entities.Country#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObjectErrorCase() {
		//Standard Case... Objects are equal
		Country countryREF = new Country("de_DE", country.getTitle(), country.getLocalTitle());
		Assert.assertFalse("equals should return false but returns true", country.equals(countryREF));
		Assert.assertFalse("equals should return false but returns true", countryREF.equals(country));
		//TODO discuss behavior if some fields are null equally
	}

	/**
	 * TODO Discussion... should equals return NPE?
	 */
	@Test(expected=NullPointerException.class)
	public void testEqualsObjectNullCase() {
		country.equals(null);
	}

	@Test
	public void testEqualsSpecialCase() {
		CountryWithCounty countryWCREF = new CountryWithCounty(country.getCode(), country.getTitle(), country.getLocalTitle(), "Wien");
		Assert.assertTrue("Equals should be transitive which is not.", countryWCREF.equals(country) == country.equals(countryWCREF));
	}

}
