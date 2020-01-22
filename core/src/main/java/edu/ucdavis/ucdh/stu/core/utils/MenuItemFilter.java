package edu.ucdavis.ucdh.stu.core.utils;

import javax.servlet.http.HttpServletRequest;

import edu.ucdavis.ucdh.stu.core.beans.MenuItem;

/**
 * <p>This is the MenuItem filter interface.</p>
 */
public interface MenuItemFilter {

	/**
	 * <p>Returns all authorized menu items.</p>
	 * 
	 * @param req the HttpServletRequest object
	 * @param menuItem the complete (unfiltered) menu
	 * @return all authorized menu items
	 */
	public MenuItem getAuthorizedMenuItems(HttpServletRequest req, MenuItem menuItem);
}
