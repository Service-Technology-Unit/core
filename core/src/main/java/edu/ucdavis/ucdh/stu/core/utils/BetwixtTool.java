package edu.ucdavis.ucdh.stu.core.utils;

import java.io.StringWriter;
import java.sql.Timestamp;
import java.util.Date;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.betwixt.io.BeanWriter;
import org.apache.commons.betwixt.strategy.ConvertUtilsObjectStringConverter;

/**
 * <p>A collection of static methods for using Betwixt.</p>
 */
public class BetwixtTool {

	/**
	 * <p>Converts a single Java bean to XML.</p>
	 * 
	 * @param bean the bean to convert
	 * @return the bean in XML format
	 */
	public static String toXml(Object bean) {
		return toXml(bean, null, true);
	}

	/**
	 * <p>Converts a single Java bean to XML.</p>
	 * 
	 * @param bean the bean to convert
	 * @param xsl the URL for the XSL stylesheet for this bean
	 * @return the bean in XML format
	 */
	public static String toXml(Object bean, String xsl) {
		return toXml(bean, xsl, true);
	}

	/**
	 * <p>Converts a single Java bean to XML.</p>
	 * 
	 * @param bean the bean to convert
	 * @param includeDeclaration when true, adds the XML declaration to the output
	 * @return the bean in XML format
	 */
	public static String toXml(Object bean, boolean includeDeclaration) {
		return toXml(bean, null, includeDeclaration);
	}

	/**
	 * <p>Converts a single Java bean to XML.</p>
	 * 
	 * @param bean the bean to convert
	 * @param xsl the URL for the XSL stylesheet for this bean
	 * @param includeDeclaration when true, adds the XML declaration to the output
	 * @return the bean in XML format
	 */
	public static String toXml(Object bean, String xsl, boolean includeDeclaration) {
		// set up ISO 8601 date convertion
		Converter dateConverter = new ISO8601DateConverter();
		ConvertUtils.register(dateConverter, Date.class);
		ConvertUtils.register(dateConverter, Timestamp.class);
		ConvertUtils.register(dateConverter, String.class);

		// set up XML bean writer
		StringWriter out = new StringWriter();
		BeanWriter writer = new BeanWriter(out);
		writer.enablePrettyPrint();
		writer.setInitialIndentLevel(0);
		writer.getBindingConfiguration().setMapIDs(false);
		writer.getBindingConfiguration().setObjectStringConverter(new ConvertUtilsObjectStringConverter());

		// create XML using bean writer
		String xml = "";
		try {
			if (includeDeclaration) {
				writer.writeXmlDeclaration("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				if (xsl != null && !"".equals(xsl.trim())) {
					writer.writeXmlDeclaration("<?xml-stylesheet type=\"text/xsl\" href=\"" + xsl + "\"?>");
				}
			}
			writer.write(bean);
			out.flush();
			xml = out.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return xml;
	}
}
