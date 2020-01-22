package edu.ucdavis.ucdh.stu.core.manager;

import java.util.Date;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobInstance;

/**
 * <p>This is the BatchJobInstance manager interface.</p>
 */
public interface BatchJobInstanceManager {

	/**
	 * <p>Returns all BatchJobInstances in the database.</p>
	 * 
	 * @return all BatchJobInstances in the database
	 */
	public List<BatchJobInstance> findAll();

	/**
	 * <p>Returns all BatchJobInstances in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobInstance an example BatchJobInstance
	 * @return all BatchJobInstances in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobInstance> findByExample(BatchJobInstance batchJobInstance);

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
	public List<BatchJobInstance> findByProperty(String propertyName, Object propertyValue);

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
	public List<BatchJobInstance> findByContextNameAndDate(String context, String name, Date startDate, Date endDate);

	/**
	 * <p>Returns the BatchJobInstance with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobInstance
	 * @return the BatchJobInstance with the specified id
	 */
	public BatchJobInstance findById(Integer id);

	/**
	 * <p>Saves the BatchJobInstance passed.</p>
	 * 
	 * @param batchJobInstance the batchJobInstance to save
	 */
	public void save(BatchJobInstance batchJobInstance);

	/**
	 * <p>Deletes the BatchJobInstance with the specified id.</p>
	 * 
	 * @param batchJobInstance the batchJobInstance to delete
	 */
	public void delete(BatchJobInstance batchJobInstance);
}
