package org.tagaprice.core.api;

import java.util.Date;

import org.tagaprice.core.entities.Account;

public class LocalAccount extends Account {
	public LocalAccount(Long uid, String email, Date lastLogin) {
		super(uid, email, lastLogin);
	}
}
