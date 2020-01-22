package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobEvent;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobEventDao;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobEventManager;

/**
 * <p>Concrete implementation of the BatchJobEvent manager interface.</p>
 */
public class BatchJobEventManagerImpl implements BatchJobEventManager {
	private BatchJobEventDao dao;

	/**
	 * <p>Returns all BatchJobEvents in the database.</p>
	 * 
	 * @return all BatchJobEvents in the database
	 */
	public List<BatchJobEvent> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all BatchJobEvents in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobEvent an example BatchJobEvent
	 * @return all BatchJobEvents in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobEvent> findByExample(BatchJobEvent batchJobEvent) {
		return dao.findByExample(batchJobEvent);
	}

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
	public List<BatchJobEvent> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns the BatchJobEvent with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobEvent
	 * @return the BatchJobEvent with the specified id
	 */
	public BatchJobEvent findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * <p>Saves the BatchJobEvent passed.</p>
	 * 
	 * @param batchJobEvent the batchJobEvent to save
	 */
	public void save(BatchJobEvent batchJobEvent) {
		dao.save(batchJobEvent);
	}

	/**
	 * <p>Deletes the BatchJobEvent with the specified id.</p>
	 * 
	 * @param batchJobEvent the batchJobEvent to delete
	 */
	public void delete(BatchJobEvent batchJobEvent) {
		dao.delete(batchJobEvent);
	}

	/**
	 * <p>Sets the BatchJobEventDao.</p>
	 * 
	 * @param batchJobEventDao the batchJobEventDao to set
	 */
	public void setBatchJobEventDao(BatchJobEventDao dao) {
		this.dao = dao;
	}
}
