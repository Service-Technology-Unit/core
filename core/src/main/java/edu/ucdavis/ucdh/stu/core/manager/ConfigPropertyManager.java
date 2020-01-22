package edu.ucdavis.ucdh.stu.core.manager;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.ConfigProperty;

/**
 * <p>This is the ConfigProperty manager interface.</p>
 */
public interface ConfigPropertyManager {

	/**
	 * <p>Returns all ConfigPropertys in the database.</p>
	 * 
	 * @return all ConfigPropertys in the database
	 */
	public List<ConfigProperty> findAll();

	/**
	 * <p>Returns all ConfigPropertys in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param configProperty an example ConfigProperty
	 * @return all ConfigPropertys in the database that
	 * match the specified search criteria
	 */
	public List<ConfigProperty> findByExample(ConfigProperty configProperty);

	/**
	 * <p>Returns all ConfigPropertys in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all ConfigPropertys in the database that
	 * match the specified search criteria
	 */
	public List<ConfigProperty> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns all ConfigPropertys in the database for the specified
	 * context.</p>
	 * 
	 * @param context the requested context
	 * @return all ConfigPropertys in the database for the specified
	 * context
	 */
	public List<ConfigProperty> findByContext(String context);

	/**
	 * <p>Returns the ConfigProperty with the specified context and
	 * property name.</p>
	 * 
	 * @param context the context of the requested ConfigProperty
	 * @param propertyName the name of the requested ConfigProperty
	 * @return the ConfigProperty with the specified context and
	 * property name
	 */
	public ConfigProperty findByContextAndPropertyName(String context, String propertyName);

	/**
	 * <p>Returns the ConfigProperty with the specified id.</p>
	 * 
	 * @param id the id of the requested configProperty
	 * @return the ConfigProperty with the specified id
	 */
	public ConfigProperty findById(String id);

	/**
	 * <p>Saves the ConfigProperty passed.</p>
	 * 
	 * @param configProperty the configProperty to save
	 */
	public void save(ConfigProperty configProperty);

	/**
	 * <p>Deletes the ConfigProperty with the specified id.</p>
	 * 
	 * @param configProperty the configProperty to delete
	 */
	public void delete(ConfigProperty configProperty);
}
