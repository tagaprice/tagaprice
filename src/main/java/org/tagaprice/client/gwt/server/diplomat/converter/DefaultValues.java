package org.tagaprice.client.gwt.server.diplomat.converter;

import java.util.Date;

import org.tagaprice.core.entities.*;

class DefaultValues {

	// dummy values
	public static final Locale defaultCoreLocale = new Locale(1, "de", "de");
	public static final Date defaultDate = new Date();
	public static final Locale defaultLocale = new Locale(1, "de", "de");
	public static final Account defaultCoreAccount = new Account(1L, "love@you.org", "super", DefaultValues.defaultDate);
}
