package org.tagaprice.shared;

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
	
}
