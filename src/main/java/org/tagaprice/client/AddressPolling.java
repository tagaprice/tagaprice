/*
 * Copyright 2010 TagAPrice.org
 * 
 * Licensed under the Creative Commons License. You may not
 * use this file except in compliance with the License. 
 *
 * http://creativecommons.org/licenses/by-nc/3.0/
*/

package org.tagaprice.client;

import org.tagaprice.shared.Address;
import org.tagaprice.shared.Country;

import com.google.code.gwt.geolocation.client.Geolocation;
import com.google.code.gwt.geolocation.client.Position;
import com.google.code.gwt.geolocation.client.PositionCallback;
import com.google.code.gwt.geolocation.client.PositionError;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.maps.client.geocode.Geocoder;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.user.client.Timer;

/**
 * Relocate automatically the current address per GPS request
 *
 */
public class AddressPolling {
	private Address address = new Address("Flossgasse","vienna",new Country("at", "Austria", "Österreich"), 48.21960,16.37205);
	private Geolocation geolocation;
	private Geocoder geocoder;
	
	/**
	 * 
	 * @param periodMillis Reload Periode
	 */
	public AddressPolling(int periodMillis){
		reloadAddress();				

		Timer t = new Timer() {
			
			@Override
			public void run() {
				reloadAddress();				
			}
		};
		
		t.scheduleRepeating(periodMillis);
	}
	

	
	/**
	 * 
	 * @return Returns the current address
	 */
	public Address getCurrentAddress(){
		System.out.println("address");
		return address;
	}
	
	/**
	 * 
	 * @param address Sets the current address
	 */
	public void setCurrentAddress(Address address){
		this.address=address;
	}
	
	private void reloadAddress(){
		if(geolocation==null) geolocation=Geolocation.getGeolocation();
		if(geocoder==null) geocoder=new Geocoder();
		
		geolocation.getCurrentPosition(new PositionCallback() {
			
			@Override
			public void onSuccess(Position position) {
				address.setCoordinates(
						position.getCoords().getLatitude(), 
						position.getCoords().getLongitude());
				
				geocoder.getLocations(
						LatLng.newInstance(address.getLat(), address.getLng()), 
						new LocationCallback() {
							
							@Override
							public void onSuccess(JsArray<Placemark> locations) {
								address.setAddress(
										locations.get(0).getStreet(), 
										locations.get(0).getCity(), 
										new Country(
												locations.get(0).getCountry().toLowerCase(), 
												locations.get(0).getCountry(), 
												locations.get(0).getCountry()));
																
								System.out.println("ready");
							}
							
							@Override
							public void onFailure(int statusCode) {
								System.out.println("can't locate Address");
							}
						});
			}
			
			@Override
			public void onFailure(PositionError error) {
				// TODO Auto-generated method stub
				System.out.println("can't locate LatLng");
			}
		});
		
	
	
	}
}
