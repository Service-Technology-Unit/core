package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.MenuItem;

/**
 * <p>This is the MenuItem data access object interface.</p>
 */
public interface MenuItemDao extends Dao {

	/**
	 * <p>Returns all MenuItems in the database.</p>
	 * 
	 * @return all MenuItems in the database
	 */
	public List<MenuItem> findAll();

	/**
	 * <p>Returns all MenuItems in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param menuItem an example MenuItem
	 * @return all MenuItems in the database that
	 * match the specified search criteria
	 */
	public List<MenuItem> findByExample(MenuItem menuItem);

	/**
	 * <p>Returns all MenuItems in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all MenuItems in the database that
	 * match the specified search criteria
	 */
	public List<MenuItem> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the MenuItem with the specified context and name.</p>
	 * 
	 * @param context the context of the requested MenuItem
	 * @param name the menuItemname of the requested MenuItem
	 * @return the MenuItem with the specified context and name
	 */
	public MenuItem findByContextAndName(String context, String name);

	/**
	 * <p>Returns the MenuItem with the specified id.</p>
	 * 
	 * @param id the id of the requested menuItem
	 * @return the MenuItem with the specified id
	 */
	public MenuItem findById(Serializable id);

	/**
	 * <p>Saves the MenuItem passed.</p>
	 * 
	 * @param menuItem the menuItem to save
	 */
	public void save(MenuItem menuItem);

	/**
	 * <p>Deletes the MenuItem with the specified id.</p>
	 * 
	 * @param menuItem the menuItem to delete
	 */
	public void delete(MenuItem menuItem);
}
