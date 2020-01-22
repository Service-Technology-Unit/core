package edu.ucdavis.ucdh.stu.core.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.ucdavis.ucdh.stu.core.beans.ConfigProperty;
import edu.ucdavis.ucdh.stu.core.manager.ConfigPropertyManager;

/**
 * <p>This is the ConfigProperty service.</p>
 */
public class ConfigPropertyService {
	private String context = null;
	private ConfigPropertyManager configPropertyManager = null;

	/**
	 * <p>Returns a list of configuration properties in this context.</p>
	 * 
	 * @return a list of configuration properties in this context
	 */
	public Map<String,String> getProperties() {
		Map<String,String> properties = new TreeMap<String,String>();

		List<ConfigProperty> list = configPropertyManager.findByContext(context);
		if (list != null && list.size() > 0) {
			Iterator<ConfigProperty> i = list.iterator();
			while (i.hasNext()) {
				ConfigProperty thisProperty = i.next();
				properties.put(thisProperty.getName(), thisProperty.getValue());
			}
		}

		return properties;
	}

	/**
	 * <p>Returns a list of lookup property names in this context.</p>
	 * 
	 * @return a list of lookup property names in this context
	 */
	public List<String> getPropertyNames() {
		List<String> propertyNames = new ArrayList<String>();

		List<ConfigProperty> propertyInfo = configPropertyManager.findByContext(context);
		if (propertyInfo != null) {
			Iterator<ConfigProperty> i = propertyInfo.iterator();
			while (i.hasNext()) {
				propertyNames.add(i.next().getName());
			}
		}

		return propertyNames;
	}

	/**
	 * <p>Returns the value of the requested lookup property.</p>
	 * 
	 * @param propertyName the name of the requested property
	 * @return the value of the requested lookup property
	 */
	public String getProperty(String propertyName) {
		String property = null;

		ConfigProperty configProperty = configPropertyManager.findByContextAndPropertyName(context, propertyName);
		if (configProperty != null) {
			property = configProperty.getValue();
		}

		return property;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}
	/**
	 * @param configPropertyManager the configPropertyManager to set
	 */
	public void setConfigPropertyManager(ConfigPropertyManager configPropertyManager) {
		this.configPropertyManager = configPropertyManager;
	}
}
