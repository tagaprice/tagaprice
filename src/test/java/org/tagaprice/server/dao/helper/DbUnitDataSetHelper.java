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

	//
	// object getter
	//

	//	/**
	//	 * @return a {@link Locale} created from given table and row within the table
	//	 */
	//	public static Locale getLocale(ITable table, int row) throws DataSetException, ParseException {
	//
	//		int id = DbUnitDataSetHelper.getInteger(table.getValue(row, "locale_id"));
	//		String title = (String) table.getValue(row, "title");
	//		String localTitle = (String) table.getValue(row, "localtitle");
	//		Date createdAt = DbUnitDataSetHelper.getDate(table.getValue(row, "created_at"));
	//
	//		Locale locale = new Locale(null, title, localTitle, createdAt);
	//		ReflectionTestUtils.invokeSetterMethod(locale, "setId", id);
	//
	//		//TODO use real fallback id
	//		ReflectionTestUtils.invokeSetterMethod(locale, "setFallback", locale);
	//
	//		return locale;
	//	}

}
