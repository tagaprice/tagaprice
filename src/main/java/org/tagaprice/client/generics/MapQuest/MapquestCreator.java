package org.tagaprice.client.generics.MapQuest;

import org.tagaprice.shared.entities.Address;

public class MapquestCreator {

	public static Address getAddressByMapquestResponse(MapquestResponse response){
		Address at = new Address();
		
		if(response.getAddress()!=null){
			String responseString = "";
			
			if(response.getAddress().getRoad()!=null){
				responseString+=response.getAddress().getRoad();
				at.setStreet(response.getAddress().getRoad());
			}
				
			if(response.getAddress().getPedestrian()!=null){
				responseString+=response.getAddress().getPedestrian();
				at.setStreet(response.getAddress().getPedestrian());
			}
			
			if(response.getAddress().getFootway()!=null){
				responseString+=response.getAddress().getFootway();
				at.setStreet(response.getAddress().getFootway());
			}
			
			if(response.getAddress().getHouse_number()!=null){
				responseString+=" "+response.getAddress().getHouse_number()+",";
				if(at.getStreet()!=null)
					at.setStreet(at.getStreet()+" "+response.getAddress().getHouse_number());
			}
			
			if(response.getAddress().getPostcode()!=null){
				responseString+=" "+response.getAddress().getPostcode()+",";
				at.setPostalcode(response.getAddress().getPostcode());
			}
			
			if(response.getAddress().getCity()!=null){
				responseString+=" "+response.getAddress().getCity()+",";
				at.setCity(response.getAddress().getCity());
			}
				
			if(response.getAddress().getCountry_code()!=null){
				responseString+=" "+response.getAddress().getCountry_code()+",";
				at.setCountrycode(response.getAddress().getCountry_code());
			}

			return at;
		}
		
		return null;
	}
}
