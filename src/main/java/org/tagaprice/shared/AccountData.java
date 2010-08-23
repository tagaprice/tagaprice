package org.tagaprice.shared;

public class AccountData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username; 
	private String password;
	private String email;
	private String language;
	private String street;
	private String zip;
	private String county;
	private String country;
	private double latitude;
	private double longitude;
	
	public AccountData() {
	}
	
	/**
	 * 
	 * @param username
	 * @param password
	 * @param email
	 * @param language
	 * @param street
	 * @param zip
	 * @param county
	 * @param country
	 * @param latitude
	 * @param longitude
	 */
	public AccountData(
			String username, 
			String password, 
			String email, 
			String language, 
			String street, 
			String zip, 
			String county,
			String country, 
			double latitude, 
			double longitude){
		setUsername(username);
		setPassword(password);
		setEmail(email);
		setLanguage(language);
		setStreet(street);
		setZip(zip);
		setCounty(county);
		setCountry(country);
		setLanguage(language);
		setLanguage(language);
	}

	public String getUsername() {
		return username;
	}



	public void setUsername(String username) {
		this.username = username;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getLanguage() {
		return language;
	}



	public void setLanguage(String language) {
		this.language = language;
	}



	public String getStreet() {
		return street;
	}



	public void setStreet(String street) {
		this.street = street;
	}



	public String getZip() {
		return zip;
	}



	public void setZip(String zip) {
		this.zip = zip;
	}



	public String getCounty() {
		return county;
	}



	public void setCounty(String county) {
		this.county = county;
	}



	public String getCountry() {
		return country;
	}



	public void setCountry(String country) {
		this.country = country;
	}



	public double getLatitude() {
		return latitude;
	}



	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}



	public double getLongitude() {
		return longitude;
	}



	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}



	@Override
	public String getSerializeName() {
		return "AccountData";
	}
	
	
}
