package org.tagaprice.shared.data;

import org.tagaprice.shared.Serializable;

public class Country implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code = null;
	private String title = null;
	private String localTitle = null;

	public Country() {
		
	}
	
	public Country(String code, String title, String localTitle) {
		this.code = code;
		this.title = title;
		this.localTitle = localTitle;
	}
	
	@Override
	public String getSerializeName() {
		return "country";
	}

	public String getCode() {
		return code;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String getLocalTitle() {
		return localTitle;
	}
	
	public boolean equals(Object o) {
		boolean rc = true;
		
		if (o instanceof Country) {
			Country c = (Country) o;
			
			if (!Entity._compare(getCode(), c.getCode())) rc = false;
			if (!Entity._compare(getTitle(), c.getTitle())) rc = false;
			if (!Entity._compare(getLocalTitle(), c.getLocalTitle())) rc = false;
		}
		else rc = false;
		
		return rc;
	}
}
