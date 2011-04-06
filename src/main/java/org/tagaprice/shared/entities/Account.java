package org.tagaprice.shared.entities;

import org.svenson.JSONProperty;

public class Account extends User {
	private String m_mail;
	
	@JSONProperty(value="mail")
	public String getMail() {
		return m_mail;
	}
}
