package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.orm.ObjectRetrievalFailureException;

import edu.ucdavis.ucdh.stu.core.beans.NoticeTemplate;
import edu.ucdavis.ucdh.stu.core.dao.NoticeTemplateDao;

/**
 * <p>This is the NoticeTemplate data access object.</p>
 */
public class NoticeTemplateDaoHibernate extends AbstractHibernateUpdateDao<NoticeTemplate> implements NoticeTemplateDao {

	/**
	 * <p>Constructs a new NoticeTemplateDaoHibernate using the parameters provided.</p>
	 */
	public NoticeTemplateDaoHibernate(SessionFactory sessionFactory) {
		super(NoticeTemplate.class, sessionFactory);
	}

	/**
	 * <p>Returns the NoticeTemplate with the specified name and context.</p>
	 * 
	 * @param context the context of the requested noticeTemplate
	 * @param name the name of the requested noticeTemplate
	 * @return the NoticeTemplate with the specified context and name
	 */
	public NoticeTemplate findByContextAndName(String context, String name) {
		NoticeTemplate noticeTemplate = new NoticeTemplate();
		noticeTemplate.setName(name);
		noticeTemplate.setContext(context);
		List<NoticeTemplate> noticeTemplates = findByExample(noticeTemplate);
		if (noticeTemplates != null && noticeTemplates.size() > 0) {
			noticeTemplate = noticeTemplates.get(0);
		} else {
			throw new ObjectRetrievalFailureException(NoticeTemplate.class, context + "/" + name);
		}
		return noticeTemplate;
	}

	/**
	 * <p>Returns all objects of this entity that satisfy the specified query.</p>
	 *
	 * @param hql the HQL statement to execute
	 * @return all objects of this entity that satisfy the specified query
	 */
	public List<NoticeTemplate> executeQuery(String hql) {
		return list(query(hql));
	}
}
