package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.Page;
import edu.ucdavis.ucdh.stu.core.dao.PageDao;

/**
 * <p>This is the Page data access object.</p>
 */
public class PageDaoHibernate extends AbstractHibernateUpdateDao<Page> implements PageDao {

	/**
	 * <p>Constructs a new PageDaoHibernate using the parameters provided.</p>
	 */
	public PageDaoHibernate(SessionFactory sessionFactory) {
		super(Page.class, sessionFactory);
	}

	/**
	 * <p>Returns the Page with the specified context and name.</p>
	 * 
	 * @param context the context of the requested Page
	 * @param name the name of the requested Page
	 * @return the Page with the specified context and name
	 */
	public Page findByContextAndName(String context, String name) {
		Page page = new Page();
		page.setId(null);
		page.setContext(context);
		page.setName(name);
		List<Page> pageEntries = findByExample(page);
		if (pageEntries != null && pageEntries.size() > 0) {
			page = pageEntries.get(0);
		} else {
			page = null;
		}
		return page;
	}
}