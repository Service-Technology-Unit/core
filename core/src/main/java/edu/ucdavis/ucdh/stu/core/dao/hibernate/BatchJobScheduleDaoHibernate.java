package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.util.List;
import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobSchedule;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobScheduleDao;
import edu.ucdavis.ucdh.stu.core.dao.hibernate.AbstractHibernateUpdateDao;

/**
 * <p>This is the BatchJobSchedule data access object.</p>
 */
public class BatchJobScheduleDaoHibernate extends AbstractHibernateUpdateDao<BatchJobSchedule> implements BatchJobScheduleDao {

	/**
	 * <p>Constructs a new BatchJobScheduleDaoHibernate using the parameters provided.</p>
	 */
	public BatchJobScheduleDaoHibernate(SessionFactory sessionFactory) {
		super(BatchJobSchedule.class, sessionFactory);
	}

	/**
	 * <p>Returns the BatchJobSchedule with the specified batchJobId.</p>
	 * 
	 * @param batchJobId the id of the BatchJob for which BatchJobSchedules
	 * are being requested
	 * @return the BatchJobSchedules associated with the BatchJob for the specified id
	 */
	public List<BatchJobSchedule> findByBatchJobId(Integer batchJobId) {
		return list(query("from BatchJobSchedule where batchJob.id = " + batchJobId));
	}

	@Override
	public BatchJobSchedule findByContextJobSchedule(String context,
			String jobName, String schlName) {
		String hQuery = "from BatchJobSchedule as s where s.name='" +
		schlName + "' and s.batchJob.name='" + jobName + "' and s.batchJob.context='" + context + "'";
		BatchJobSchedule bSchedule = null;
		try{
			bSchedule = uniqueResult(query(hQuery));
		}catch(RuntimeException e ){
			return null;
		}
		return bSchedule;
	}


}