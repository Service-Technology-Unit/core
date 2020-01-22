package edu.ucdavis.ucdh.stu.core.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>This entity bean defines a single look-up table.</p>
 */
public class LookupTable extends PersistentBeanBase {
	private static final long serialVersionUID = 1;
	private int id = -1;
	private String context = null;
	private String tableName = null;
	private String displayName = null;
	private String description = null;
	private List<LookupTableProperty> properties = null;

	public void addProperty(LookupTableProperty lookupTableProperty) {
		if (properties == null) {
			properties = new ArrayList<LookupTableProperty>();
		}
		lookupTableProperty.setTableId(id);
		lookupTableProperty.setSequence(properties.size());
		properties.add(lookupTableProperty);
	}

	public LookupTableProperty getProperty(int sequence) {
		LookupTableProperty lookupTableProperty = null;

		if (properties != null && properties.size() > sequence) {
			lookupTableProperty = (LookupTableProperty) properties.get(sequence);
		}

		return lookupTableProperty;
	}

	public void setProperty(int sequence, LookupTableProperty lookupTableProperty) {
		if (properties != null && properties.size() > sequence) {
			properties.set(sequence, lookupTableProperty);
		}
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the context
	 */
	public String getContext() {
		return context;
	}

	/**
	 * @param context the context to set
	 */
	public void setContext(String context) {
		this.context = context;
	}

	/**
	 * @return the tableName
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * @param tableName the tableName to set
	 */
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the properties
	 */
	public List<LookupTableProperty> getProperties() {
		return properties;
	}

	/**
	 * @param properties the properties to set
	 */
	public void setProperties(List<LookupTableProperty> properties) {
		this.properties = properties;
	}
}
