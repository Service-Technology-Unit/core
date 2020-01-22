package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.NoticeAudit;
import edu.ucdavis.ucdh.stu.core.dao.NoticeAuditDao;

/**
 * <p>This is the NoticeAudit data access object.</p>
 */
public class NoticeAuditDaoHibernate extends AbstractHibernateUpdateDao<NoticeAudit> implements NoticeAuditDao {

	/**
	 * <p>Constructs a new NoticeAuditDaoHibernate using the parameters provided.</p>
	 */
	public NoticeAuditDaoHibernate(SessionFactory sessionFactory) {
		super(NoticeAudit.class, sessionFactory);
	}
}