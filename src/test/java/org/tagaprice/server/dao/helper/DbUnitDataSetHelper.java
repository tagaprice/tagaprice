package org.tagaprice.server.dao.helper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DbUnitDataSetHelper {

	private static DateFormat s_dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSSSSS");


	public static Date getDate(Object dataSetValue) throws ParseException {
		return DbUnitDataSetHelper.s_dateFormatter.parse((String) dataSetValue);
	}

	public static Integer getInteger(Object dataSetValue) {
		return Integer.parseInt((String) dataSetValue);
	}

	public static Long getLong(Object dataSetValue) {
		return Long.parseLong((String) dataSetValue);
	}
}
