package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobSchedule;

/**
 * <p>This is the BatchJobSchedule data access object interface.</p>
 */
public interface BatchJobScheduleDao extends Dao {

	/**
	 * <p>Returns all BatchJobSchedules in the database.</p>
	 * 
	 * @return all BatchJobSchedules in the database
	 */
	public List<BatchJobSchedule> findAll();

	/**
	 * <p>Returns all BatchJobSchedules in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobSchedule an example BatchJobSchedule
	 * @return all BatchJobSchedules in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobSchedule> findByExample(BatchJobSchedule batchJobSchedule);

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
	public List<BatchJobSchedule> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the BatchJobSchedule with the specified batchJobId.</p>
	 * 
	 * @param batchJobId the id of the BatchJob for which BatchJobSchedules
	 * are being requested
	 * @return the BatchJobSchedules associated with the BatchJob for the specified id
	 */
	public List<BatchJobSchedule> findByBatchJobId(Integer batchJobId);

	/**
	 * <p>Returns the BatchJobSchedule with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobSchedule
	 * @return the BatchJobSchedule with the specified id
	 */
	public BatchJobSchedule findById(Serializable id);
	
	/**
	 * <p>Returns the BatchJobSchedule with the specified applicationId, jobName and scheduleName.</p>
	 * 
	 * @param applicationId the application id of the requested batchJobSchedule
	 * @param jobName the job name of the requested batchJobSchedule
	 * @param scheduleName the schedule name of the requested batchJobSchedule
	 * @return the BatchJobSchedule found by the parameters
	 */
	public BatchJobSchedule findByContextJobSchedule(String context, String jobName, String schlName);

	/**
	 * <p>Saves the BatchJobSchedule passed.</p>
	 * 
	 * @param batchJobSchedule the batchJobSchedule to save
	 */
	public void save(BatchJobSchedule batchJobSchedule);

	/**
	 * <p>Deletes the BatchJobSchedule with the specified id.</p>
	 * 
	 * @param batchJobSchedule the batchJobSchedule to delete
	 */
	public void delete(BatchJobSchedule batchJobSchedule);
}
