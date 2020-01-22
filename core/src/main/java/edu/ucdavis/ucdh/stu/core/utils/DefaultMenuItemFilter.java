package edu.ucdavis.ucdh.stu.core.utils;

import javax.servlet.http.HttpServletRequest;

import edu.ucdavis.ucdh.stu.core.beans.MenuItem;

/**
 * <p>This default implementation of the MenuItem filter
 * interface simply returns the entire menu structure without
 * filtering out any of the menu items.</p>
 */
public class DefaultMenuItemFilter implements MenuItemFilter {

	/**
	 * <p>Returns all authorized menu items.</p>
	 * 
	 * @param req the HttpServletRequest object
	 * @param menuItem the complete (unfiltered) menu
	 * @return all authorized menu items
	 */
	public MenuItem getAuthorizedMenuItems(HttpServletRequest req, MenuItem menuItem) {
		return menuItem;
	}

}
