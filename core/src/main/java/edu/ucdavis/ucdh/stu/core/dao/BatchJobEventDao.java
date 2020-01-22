package edu.ucdavis.ucdh.stu.core.dao;

import java.io.Serializable;
import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobEvent;

/**
 * <p>This is the BatchJobEvent data access object interface.</p>
 */
public interface BatchJobEventDao extends Dao {

	/**
	 * <p>Returns all BatchJobEvents in the database.</p>
	 * 
	 * @return all BatchJobEvents in the database
	 */
	public List<BatchJobEvent> findAll();

	/**
	 * <p>Returns all BatchJobEvents in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobEvent an example BatchJobEvent
	 * @return all BatchJobEvents in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobEvent> findByExample(BatchJobEvent batchJobEvent);

	/**
	 * <p>Returns all BatchJobEvents in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param propertyName the name of the specified property
	 * @param propertyValue the search value for the specified
	 * property
	 * @return all BatchJobEvents in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobEvent> findByProperty(String propertyName, Object propertyValue);

	/**
	 * <p>Returns the BatchJobEvent with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobEvent
	 * @return the BatchJobEvent with the specified id
	 */
	public BatchJobEvent findById(Serializable id);

	/**
	 * <p>Saves the BatchJobEvent passed.</p>
	 * 
	 * @param batchJobEvent the batchJobEvent to save
	 */
	public void save(BatchJobEvent batchJobEvent);

	/**
	 * <p>Deletes the BatchJobEvent with the specified id.</p>
	 * 
	 * @param batchJobEvent the batchJobEvent to delete
	 */
	public void delete(BatchJobEvent batchJobEvent);
}
