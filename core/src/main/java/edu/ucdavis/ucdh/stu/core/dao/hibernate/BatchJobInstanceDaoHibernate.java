package edu.ucdavis.ucdh.stu.core.dao.hibernate;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobInstance;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobStatistic;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobInstanceDao;

/**
 * <p>This is the BatchJobInstance data access object.</p>
 * <p>This is a nonstandard DAO due to the double Lists that seem to be
 * causing Hibernate some hearburn when fetched eagerly.</p>
 */
public class BatchJobInstanceDaoHibernate extends AbstractHibernateUpdateDao<BatchJobInstance> implements BatchJobInstanceDao {
	private Log log = LogFactory.getLog(getClass());

	/**
	 * <p>Constructs a new BatchJobInstanceDaoHibernate using the parameters provided.</p>
	 */
	public BatchJobInstanceDaoHibernate(SessionFactory sessionFactory) {
		super(BatchJobInstance.class, sessionFactory);
	}

	/**
	 * <p>Returns all BatchJobInstances in the database.</p>
	 * 
	 * @return all BatchJobInstances in the database
	 */
	public List<BatchJobInstance> findAll() {
		List<BatchJobInstance> list = super.findAll();
		fetchAllStatistics(list);
		return list;
	}

	/**
	 * <p>Returns all BatchJobInstances in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobInstance an example BatchJobInstance
	 * @return all BatchJobInstances in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobInstance> findByExample(BatchJobInstance batchJobInstance) {
		List<BatchJobInstance> list = super.findByExample(batchJobInstance);
		fetchAllStatistics(list);
		return list;
	}

	/**
	 * <p>Returns all BatchJobInstances in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all BatchJobInstances in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobInstance> findByProperty(String propertyName, Object propertyValue) {
		List<BatchJobInstance> list = super.findByProperty(propertyName, propertyValue);
		fetchAllStatistics(list);
		return list;
	}

	/**
	 * <p>Returns all BatchJobInstances in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param context the context of the job
	 * @param name the name of the job
	 * @param startDate the inclusive begin date of the desired run time period
	 * @param endDate the inclusive end date of the desired run time period
	 * @return all BatchJobInstances in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobInstance> findByContextNameAndDate(String context, String name, Date startDate, Date endDate) {
		String hql = "from BatchJobInstance bji";
		String whereAnd = "where";
		if (StringUtils.isNotEmpty(context)) {
			hql += " " + whereAnd + " bji.batchJobSchedule.batchJob.context = '" + context + "'";
			whereAnd = "and";
		}
		if (StringUtils.isNotEmpty(name)) {
			hql += " " + whereAnd + " bji.batchJobSchedule.batchJob.name = '" + name + "'";
			whereAnd = "and";
		}
		if (startDate != null) {
			hql += " " + whereAnd + " (bji.startDateTime is null or bji.startDateTime > '" + fixDate(startDate) + "')";
			whereAnd = "and";
		}
		if (endDate != null) {
			hql += " " + whereAnd + " (bji.startDateTime is null or bji.startDateTime < '" + fixDate(new Date(endDate.getTime() + 86400000)) + "')";
			whereAnd = "and";
		}
		hql += " order by  bji.id desc";
		List<BatchJobInstance> list = list(query(hql));
		fetchAllStatistics(list);
		return list;
	}

	private String fixDate(Date date) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String string = df.format(date);
		if (log.isDebugEnabled()) {
			log.debug(date + " converted to " + string);
		}
		return string;
	}
	/**
	 * <p>Returns the BatchJobInstance with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobInstance
	 * @return the BatchJobInstance with the specified id
	 */
	public BatchJobInstance findById(Serializable id) {
		BatchJobInstance batchJobInstance = super.findById(id);
		fetchStatistics(batchJobInstance);
		return batchJobInstance;
	}

	/**
	 * <p>Forces a fetch of the statistics for this instance.</p>
	 * 
	 * @param batchJobInstance the BatchJobInstance
	 */
	private void fetchAllStatistics(List<BatchJobInstance> batchJobInstances) {
		if (batchJobInstances != null) {
			Iterator<BatchJobInstance> i = batchJobInstances.iterator();
			while (i.hasNext()) {
				fetchStatistics(i.next());
			}
		}
	}

	/**
	 * <p>Forces a fetch of the statistics for this instance.</p>
	 * 
	 * @param batchJobInstance the BatchJobInstance
	 */
	private void fetchStatistics(BatchJobInstance batchJobInstance) {
		if (batchJobInstance != null) {
			List<BatchJobStatistic> list = batchJobInstance.getStatistic();
			if (list != null) {
				Iterator<BatchJobStatistic> i = list.iterator();
				while (i.hasNext()) {
					i.next();
				}
			}
		}
	}
}