package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.MenuItem;
import edu.ucdavis.ucdh.stu.core.dao.MenuItemDao;

/**
 * <p>This is the MenuItem data access object.</p>
 */
public class MenuItemDaoHibernate extends AbstractHibernateUpdateDao<MenuItem> implements MenuItemDao {

	/**
	 * <p>Constructs a new MenuItemDaoHibernate using the parameters provided.</p>
	 */
	public MenuItemDaoHibernate(SessionFactory sessionFactory) {
		super(MenuItem.class, sessionFactory);
	}

	/**
	 * <p>Returns the MenuItem with the specified context and name.</p>
	 * 
	 * @param context the context of the requested MenuItem
	 * @param name the name of the requested MenuItem
	 * @return the MenuItem with the specified context and name
	 */
	public MenuItem findByContextAndName(String context, String name) {
		MenuItem menuItem = new MenuItem();
		menuItem.setId(null);
		menuItem.setContext(context);
		menuItem.setName(name);
		List<MenuItem> menuItemEntries = findByExample(menuItem);
		if (menuItemEntries != null && menuItemEntries.size() > 0) {
			menuItem = null;
			for (int i=0; i<menuItemEntries.size() && menuItem == null; i++) {
				if (menuItemEntries.get(i).getParent() == null) {
					menuItem = menuItemEntries.get(i);
				}
			}
		} else {
			menuItem = null;
		}
		return menuItem;
	}
}