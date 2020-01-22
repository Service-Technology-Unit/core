package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.Page;

/**
 * <p>This is the Page data access object interface.</p>
 */
public interface PageDao extends Dao {

	/**
	 * <p>Returns all Pages in the database.</p>
	 * 
	 * @return all Pages in the database
	 */
	public List<Page> findAll();

	/**
	 * <p>Returns all Pages in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param page an example Page
	 * @return all Pages in the database that
	 * match the specified search criteria
	 */
	public List<Page> findByExample(Page page);

	/**
	 * <p>Returns all Pages in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all Pages in the database that
	 * match the specified search criteria
	 */
	public List<Page> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the Page with the specified context and name.</p>
	 * 
	 * @param context the context of the requested Page
	 * @param name the name of the requested Page
	 * @return the Page with the specified context and name
	 */
	public Page findByContextAndName(String context, String name);

	/**
	 * <p>Returns the Page with the specified id.</p>
	 * 
	 * @param id the id of the requested page
	 * @return the Page with the specified id
	 */
	public Page findById(Serializable id);

	/**
	 * <p>Saves the Page passed.</p>
	 * 
	 * @param page the page to save
	 */
	public void save(Page page);

	/**
	 * <p>Deletes the Page with the specified id.</p>
	 * 
	 * @param page the page to delete
	 */
	public void delete(Page page);
}
