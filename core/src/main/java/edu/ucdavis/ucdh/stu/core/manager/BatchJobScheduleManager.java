package edu.ucdavis.ucdh.stu.core.manager;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobSchedule;

/**
 * <p>This is the BatchJobSchedule manager interface.</p>
 */
public interface BatchJobScheduleManager {

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
	 * <p>Returns all BatchJobSchedules in the database for the
	 * BatchJob identified by the passed context and name.</p>
	 * 
	 * @param context the context of the BatchJob for the requested schedules
	 * @param name the name of the BatchJob for the requested schedules
	 * @return all schedules for the BatchJob with the specified context and name
	 */
	public List<BatchJobSchedule> findByContextAndName(String context, String name);

	/**
	 * <p>Returns the BatchJobSchedule with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobSchedule
	 * @return the BatchJobSchedule with the specified id
	 */
	public BatchJobSchedule findById(Integer id);

	/**
	 * <p>Returns the BatchJobSchedule with the specified id.</p>
	 * 
	 * @param context the context of the BatchJob for the requested schedule
	 * @param jobName the name of the BatchJob for the requested schedule
	 * @param scheduleName the name of the BatchJobSchedule for the requested schedule
	 * @return the BatchJobSchedule with the specified context, jobName, and scheduleName
	 */
	public BatchJobSchedule findByContextJobSchedule(String context, String jobName, String scheduleName);

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
