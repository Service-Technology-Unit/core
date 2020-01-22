package edu.ucdavis.ucdh.stu.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import edu.ucdavis.ucdh.stu.core.beans.MenuItem;

/**
 * <p>This MenuItem filter uses the user role to filter out
 * unauthorized menu items.</p>
 */
public class RoleBasedMenuItemFilter implements MenuItemFilter {
	Log log = LogFactory.getLog(getClass());

	/**
	 * <p>Returns all authorized menu items.</p>
	 * 
	 * @param req the HttpServletRequest object
	 * @param menuItem the complete (unfiltered) menu
	 * @return all authorized menu items
	 */
	public MenuItem getAuthorizedMenuItems(HttpServletRequest req, MenuItem menuItem) {
		MenuItem returnItem = null;

		boolean isAuthorized = false;
		if (StringUtils.isEmpty(menuItem.getAuthorizedRoles())) {
			isAuthorized = true;
		} else {
			String[] roles = menuItem.getAuthorizedRoles().split(",");
			for (int i=0; i<roles.length; i++) {
				if (req.isUserInRole(roles[i])) {
					isAuthorized = true;
				}
			}
		}
		if (isAuthorized) {
			if (log.isDebugEnabled()) {
				log.debug("Authorizing menu item " + menuItem.getName() + " for user " + req.getRemoteUser());
			}
			returnItem = menuItem;
			if (menuItem.getMenuItem() != null && menuItem.getMenuItem().size() > 0) {
				List<MenuItem> items = new ArrayList<MenuItem>(menuItem.getMenuItem());
				menuItem.getMenuItem().removeAll(items);
				Iterator<MenuItem> i = items.iterator();
				while (i.hasNext()) {
					MenuItem thisItem = this.getAuthorizedMenuItems(req, i.next());
					if (thisItem != null) {
						returnItem.addMenuItem(thisItem);
					}
				}
			}
		} else {
			if (log.isDebugEnabled()) {
				log.debug("Filtering out menu item " + menuItem.getName() + " for user " + req.getRemoteUser());
			}
		}
		
		return returnItem;
	}
}
