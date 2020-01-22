package edu.ucdavis.ucdh.stu.core.manager;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobStatistic;

/**
 * <p>This is the BatchJobStatistic manager interface.</p>
 */
public interface BatchJobStatisticManager {

	/**
	 * <p>Returns all BatchJobStatistics in the database.</p>
	 * 
	 * @return all BatchJobStatistics in the database
	 */
	public List<BatchJobStatistic> findAll();

	/**
	 * <p>Returns all BatchJobStatistics in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobStatistic an example BatchJobStatistic
	 * @return all BatchJobStatistics in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobStatistic> findByExample(BatchJobStatistic batchJobStatistic);

	/**
	 * <p>Returns all BatchJobStatistics in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all BatchJobStatistics in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobStatistic> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the BatchJobStatistic with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobStatistic
	 * @return the BatchJobStatistic with the specified id
	 */
	public BatchJobStatistic findById(Integer id);

	/**
	 * <p>Saves the BatchJobStatistic passed.</p>
	 * 
	 * @param batchJobStatistic the batchJobStatistic to save
	 */
	public void save(BatchJobStatistic batchJobStatistic);

	/**
	 * <p>Deletes the BatchJobStatistic with the specified id.</p>
	 * 
	 * @param batchJobStatistic the batchJobStatistic to delete
	 */
	public void delete(BatchJobStatistic batchJobStatistic);
}
