package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.Page;
import edu.ucdavis.ucdh.stu.core.dao.PageDao;
import edu.ucdavis.ucdh.stu.core.manager.PageManager;

/**
 * <p>Concrete implementation of the Page manager interface.</p>
 */
public class PageManagerImpl implements PageManager {
	private PageDao dao;

	/**
	 * <p>Returns all Pages in the database.</p>
	 * 
	 * @return all Pages in the database
	 */
	public List<Page> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all Pages in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param page an example Page
	 * @return all Pages in the database that
	 * match the specified search criteria
	 */
	public List<Page> findByExample(Page page) {
		return dao.findByExample(page);
	}

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
	public List<Page> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns the Page with the specified context and name.</p>
	 * 
	 * @param context the context of the requested Page
	 * @param name the pagename of the requested Page
	 * @return the Page with the specified context and name
	 */
	public Page findByContextAndName(String context, String name) {
		return dao.findByContextAndName(context, name);
	}

	/**
	 * <p>Returns the Page with the specified id.</p>
	 * 
	 * @param id the id of the requested page
	 * @return the Page with the specified id
	 */
	public Page findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * <p>Saves the Page passed.</p>
	 * 
	 * @param page the page to save
	 */
	public void save(Page page) {
		dao.save(page);
	}

	/**
	 * <p>Deletes the Page with the specified id.</p>
	 * 
	 * @param page the page to delete
	 */
	public void delete(Page page) {
		dao.delete(page);
	}

	/**
	 * <p>Sets the PageDao.</p>
	 * 
	 * @param pageDao the pageDao to set
	 */
	public void setPageDao(PageDao dao) {
		this.dao = dao;
	}
}
