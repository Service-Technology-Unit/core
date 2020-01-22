package edu.ucdavis.ucdh.stu.core.manager;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJob;

/**
 * <p>This is the BatchJob manager interface.</p>
 */
public interface BatchJobManager {

	/**
	 * <p>Returns all BatchJobs in the database.</p>
	 * 
	 * @return all BatchJobs in the database
	 */
	public List<BatchJob> findAll();

	/**
	 * <p>Returns all BatchJobs in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJob an example BatchJob
	 * @return all BatchJobs in the database that
	 * match the specified search criteria
	 */
	public List<BatchJob> findByExample(BatchJob batchJob);

	/**
	 * <p>Returns all BatchJobs in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all BatchJobs in the database that
	 * match the specified search criteria
	 */
	public List<BatchJob> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the BatchJob with the specified context and name.</p>
	 * 
	 * @param context the context of the requested BatchJob
	 * @param name the name of the requested BatchJob
	 * @return the BatchJob with the specified context and name
	 */
	public BatchJob findByContextAndName(String context, String name);

	/**
	 * <p>Returns the BatchJob with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJob
	 * @return the BatchJob with the specified id
	 */
	public BatchJob findById(Integer id);

	/**
	 * <p>Saves the BatchJob passed.</p>
	 * 
	 * @param batchJob the batchJob to save
	 */
	public void save(BatchJob batchJob);

	/**
	 * <p>Deletes the BatchJob with the specified id.</p>
	 * 
	 * @param batchJob the batchJob to delete
	 */
	public void delete(BatchJob batchJob);
}
