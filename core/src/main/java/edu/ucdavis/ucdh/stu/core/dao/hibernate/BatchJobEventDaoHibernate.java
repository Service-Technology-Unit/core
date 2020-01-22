package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobEvent;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobEventDao;

/**
 * <p>This is the BatchJobEvent data access object.</p>
 */
public class BatchJobEventDaoHibernate extends AbstractHibernateUpdateDao<BatchJobEvent> implements BatchJobEventDao {

	/**
	 * <p>Constructs a new BatchJobEventDaoHibernate using the parameters provided.</p>
	 */
	public BatchJobEventDaoHibernate(SessionFactory sessionFactory) {
		super(BatchJobEvent.class, sessionFactory);
	}
}