package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.BatchJob;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobDao;

/**
 * <p>This is the BatchJob data access object.</p>
 */
public class BatchJobDaoHibernate extends AbstractHibernateUpdateDao<BatchJob> implements BatchJobDao {

	/**
	 * <p>Constructs a new BatchJobDaoHibernate using the parameters provided.</p>
	 */
	public BatchJobDaoHibernate(SessionFactory sessionFactory) {
		super(BatchJob.class, sessionFactory);
	}

	/**
	 * <p>Returns the BatchJob with the specified context and name.</p>
	 * 
	 * @param context the context of the requested BatchJob
	 * @param name the name of the requested BatchJob
	 * @return the BatchJob with the specified context and name
	 */
	public BatchJob findByContextAndName(String context, String name) {
		BatchJob batchJob = new BatchJob();
		batchJob.setId(null);
		batchJob.setContext(context);
		batchJob.setName(name);
		List<BatchJob> batchJobEntries = findByExample(batchJob);
		if (batchJobEntries != null && batchJobEntries.size() > 0) {
			batchJob = batchJobEntries.get(0);
		} else {
			batchJob = null;
		}
		return batchJob;
	}
}