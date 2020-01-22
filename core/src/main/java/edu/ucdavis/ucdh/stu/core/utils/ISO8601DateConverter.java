package edu.ucdavis.ucdh.stu.core.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * <p>Converts java Date objects to ISO8601-compliant String values and back.</p>
 */
public class ISO8601DateConverter implements Converter {
	private static final DateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	private static final DateFormat ALT_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Object convert(Class clazz, Object object) throws ConversionException {
		Object returnValue = null;

		if (object != null) {
			if (clazz == null) {
				throw new ConversionException("Class parameter cannot be null.");
			} else {
				if (clazz.equals(Date.class) && object.getClass().equals(String.class)) {
					returnValue = convertStringToDate((String) object);
				} else if (clazz.equals(String.class)) {
					if (object.getClass().equals(Date.class) || object.getClass().equals(Timestamp.class)) {
						returnValue = convertDateToString((Date) object);
					} else {
						returnValue = object.toString();
					}
				} else {
					returnValue = object;
				}
			}
		}

		return returnValue;
	}

	public Date convertStringToDate(String string) throws ConversionException {
		try {
			return FORMAT.parse(string);
		} catch (ParseException e) {
			try {
				// try removing the colon in the Time Zone
				string = string.substring(0, string.length() - 3) + string.substring(string.length() - 2);
				return FORMAT.parse(string);
			} catch (ParseException e1) {
				try {
					// try the alternate format
					return ALT_FORMAT.parse(string);
				} catch (ParseException e2) {
					throw new ConversionException(e);
				}
			}
		}
	}

	public String convertDateToString(Date date) throws ConversionException {
		String string = FORMAT.format(date);
		return string.substring(0, string.length() - 2) + ":" + string.substring(string.length() - 2);
	}
}
