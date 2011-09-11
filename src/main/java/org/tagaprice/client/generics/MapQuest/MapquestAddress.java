package org.tagaprice.client.generics.MapQuest;

import com.google.gwt.core.client.JavaScriptObject;

public class MapquestAddress extends JavaScriptObject{
	
	protected MapquestAddress(){}
	
	public final native String getHouse_number() /*-{
		return this.house_number;
	}-*/;

	public final native void setHouse_number(String house_number) /*-{
		this.house_number = house_number;
	}-*/;
	
	
	public final native String getRoad() /*-{
		return this.road;
	}-*/;
	
	public final native void setRoad(String road) /*-{
		this.road = road;
	}-*/;
	
	public final native String getPedestrian() /*-{
		return this.pedestrian;
	}-*/;
	
	public final native void setPedestrian(String pedestrian) /*-{
		this.pedestrian = pedestrian;
	}-*/;
	
	public final native String getFootway() /*-{
		return this.footway;
	}-*/;
	
	public final native void setFootway(String footway) /*-{
		this.footway = footway;
	}-*/;
	
	public final native String getCity() /*-{
		return this.city;
	}-*/;
	
	public final native void setCity(String city) /*-{
		this.city = city;
	}-*/;
	
	public final native String getPostcode() /*-{
		return this.postcode;
	}-*/;
	
	public final native void setPostcode(String postcode) /*-{
		this.postcode = postcode;
	}-*/;
	
	public final native String getCountry() /*-{
		return this.country;
	}-*/;
	
	public final native void setCountry(String country) /*-{
		this.country = country;
	}-*/;
	
	public final native String getCountry_code() /*-{
		return this.country_code;
	}-*/;
	
	public final native void setCountry_code(String country_code) /*-{
		this.country_code = country_code;
	}-*/;
	
	public final native String print()/*-{
		
		return "house_number: "
		+this.house_number
		+", road: "+this.road
		+", pedestrian: "+this.pedestrian
		+", footway: "+this.footway
		+", suburb: "+this.suburb
		+", city_district: "+this.city_district
		+", city: "+this.city
		+", county: "+this.county
		+", region: "+this.region
		+", state: "+this.state
		+", postcode: "+this.postcode
		+", country: "+this.country
		+", country_code: "+this.country_code
		;
	}-*/;
	
	
	
	//address types
	/*
	 * house_number
	 * road
	 * pedestrian
	 * footway
	 * suburb
	 * city_district
	 * city
	 * county
	 * region
	 * state
	 * postcode
	 * country
	 * country_code
	 */
	
}