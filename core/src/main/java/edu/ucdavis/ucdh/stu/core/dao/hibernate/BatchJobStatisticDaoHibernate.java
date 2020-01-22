package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobStatistic;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobStatisticDao;

/**
 * <p>This is the BatchJobStatistic data access object.</p>
 */
public class BatchJobStatisticDaoHibernate extends AbstractHibernateUpdateDao<BatchJobStatistic> implements BatchJobStatisticDao {

	/**
	 * <p>Constructs a new BatchJobStatisticDaoHibernate using the parameters provided.</p>
	 */
	public BatchJobStatisticDaoHibernate(SessionFactory sessionFactory) {
		super(BatchJobStatistic.class, sessionFactory);
	}
}