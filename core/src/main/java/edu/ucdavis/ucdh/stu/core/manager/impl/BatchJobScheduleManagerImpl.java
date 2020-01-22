package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJob;
import edu.ucdavis.ucdh.stu.core.beans.BatchJobSchedule;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobDao;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobScheduleDao;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobScheduleManager;

/**
 * <p>Concrete implementation of the BatchJobSchedule manager interface.</p>
 */
public class BatchJobScheduleManagerImpl implements BatchJobScheduleManager {
	private BatchJobDao batchJobDao;
	private BatchJobScheduleDao dao;

	/**
	 * <p>Returns all BatchJobSchedules in the database.</p>
	 * 
	 * @return all BatchJobSchedules in the database
	 */
	public List<BatchJobSchedule> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all BatchJobSchedules in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobSchedule an example BatchJobSchedule
	 * @return all BatchJobSchedules in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobSchedule> findByExample(BatchJobSchedule batchJobSchedule) {
		return dao.findByExample(batchJobSchedule);
	}

	/**
	 * <p>Returns all BatchJobSchedules in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all BatchJobSchedules in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobSchedule> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns all BatchJobSchedules in the database for the
	 * BatchJob identified by the passed context and name.</p>
	 * 
	 * @param context the context of the BatchJob for the requested schedules
	 * @param name the name of the BatchJob for the requested schedules
	 * @return all schedules for the BatchJob with the specified context and name
	 */
	public List<BatchJobSchedule> findByContextAndName(String context, String name) {
		List<BatchJobSchedule> list = null;

		BatchJob batchJob = batchJobDao.findByContextAndName(context, name);
		if (batchJob != null) {
			list = dao.findByBatchJobId(batchJob.getId());
		}

		return list;
	}

	/**
	 * <p>Returns the BatchJobSchedule with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobSchedule
	 * @return the BatchJobSchedule with the specified id
	 */
	public BatchJobSchedule findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * <p>Returns the BatchJobSchedule with the specified id.</p>
	 * 
	 * @param context the context of the BatchJob for the requested schedule
	 * @param jobName the name of the BatchJob for the requested schedule
	 * @param scheduleName the name of the BatchJobSchedule for the requested schedule
	 * @return the BatchJobSchedule with the specified context, jobName, and scheduleName
	 */
	public BatchJobSchedule findByContextJobSchedule(String context, String jobName, String scheduleName) {
		BatchJobSchedule schedule = null;

System.out.println("context: " + context + "; jobName: " + jobName + "; scheduleName: " + scheduleName);
		BatchJob batchJob = batchJobDao.findByContextAndName(context, jobName);
System.out.println("batchJob: " + batchJob);
		if (batchJob != null) {
			BatchJobSchedule example = new  BatchJobSchedule();
System.out.println("example (0): " + example);
			example.setBatchJob(batchJob);
System.out.println("example (1): " + example);
			example.setName(scheduleName);
System.out.println("example (2): " + example);
			List<BatchJobSchedule> schedules = dao.findByExample(example);
System.out.println("schedules: " + schedules);
			if (schedules.size() > 0) {
System.out.println("schedules.size(): " + schedules.size());
				schedule = schedules.get(0);
			}
		}

System.out.println("schedule: " + schedule);

		return schedule;
	}

	/**
	 * <p>Saves the BatchJobSchedule passed.</p>
	 * 
	 * @param batchJobSchedule the batchJobSchedule to save
	 */
	public void save(BatchJobSchedule batchJobSchedule) {
		dao.save(batchJobSchedule);
	}

	/**
	 * <p>Deletes the BatchJobSchedule with the specified id.</p>
	 * 
	 * @param batchJobSchedule the batchJobSchedule to delete
	 */
	public void delete(BatchJobSchedule batchJobSchedule) {
		dao.delete(batchJobSchedule);
	}

	/**
	 * <p>Sets the BatchJobDao.</p>
	 * 
	 * @param batchJobDao the batchJobDao to set
	 */
	public void setBatchJobDao(BatchJobDao batchJobDao) {
		this.batchJobDao = batchJobDao;
	}

	/**
	 * <p>Sets the BatchJobScheduleDao.</p>
	 * 
	 * @param batchJobScheduleDao the batchJobScheduleDao to set
	 */
	public void setBatchJobScheduleDao(BatchJobScheduleDao dao) {
		this.dao = dao;
	}
}
