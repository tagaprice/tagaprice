package org.tagaprice.shared.entities.accountmanagement;

import org.svenson.JSONProperty;

public class Account extends User {
	private static final long serialVersionUID = 1L;

	private String m_mail;
	
	@JSONProperty(value="mail")
	public String getMail() {
		return m_mail;
	}
}
