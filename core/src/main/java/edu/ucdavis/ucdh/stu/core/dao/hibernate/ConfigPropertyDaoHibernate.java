package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.ConfigProperty;
import edu.ucdavis.ucdh.stu.core.dao.ConfigPropertyDao;

/**
 * <p>This is the ConfigProperty data access object.</p>
 */
public class ConfigPropertyDaoHibernate extends AbstractHibernateUpdateDao<ConfigProperty> implements ConfigPropertyDao {

	/**
	 * <p>Constructs a new ConfigPropertyDaoHibernate using the parameters provided.</p>
	 */
	public ConfigPropertyDaoHibernate(SessionFactory sessionFactory) {
		super(ConfigProperty.class, sessionFactory);
	}

	/**
	 * <p>Returns all ConfigPropertys in the database for the specified
	 * context.</p>
	 * 
	 * @param context the requested context
	 * @return all ConfigPropertys in the database for the specified
	 * context
	 */
	public List<ConfigProperty> findByContext(String context) {
		return findByProperty("context", context);
	}

	/**
	 * <p>Returns the ConfigProperty with the specified context and
	 * property name.</p>
	 * 
	 * @param context the context of the requested ConfigProperty
	 * @param propertyName the name of the requested ConfigProperty
	 * @return the ConfigProperty with the specified context and
	 * property name
	 */
	public ConfigProperty findByContextAndPropertyName(String context, String propertyName) {
		ConfigProperty configProperty = new ConfigProperty();
		configProperty.setId(null);
		configProperty.setContext(context);
		configProperty.setName(propertyName);
		List<ConfigProperty> configPropertyEntries = findByExample(configProperty);
		if (configPropertyEntries != null && configPropertyEntries.size() > 0) {
			configProperty = configPropertyEntries.get(0);
		} else {
			configProperty = null;
		}
		return configProperty;
	}
}