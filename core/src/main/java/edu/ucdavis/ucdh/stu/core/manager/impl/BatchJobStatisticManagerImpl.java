package edu.ucdavis.ucdh.stu.core.manager.impl;

import java.util.List;

import edu.ucdavis.ucdh.stu.core.beans.BatchJobStatistic;
import edu.ucdavis.ucdh.stu.core.dao.BatchJobStatisticDao;
import edu.ucdavis.ucdh.stu.core.manager.BatchJobStatisticManager;

/**
 * <p>Concrete implementation of the BatchJobStatistic manager interface.</p>
 */
public class BatchJobStatisticManagerImpl implements BatchJobStatisticManager {
	private BatchJobStatisticDao dao;

	/**
	 * <p>Returns all BatchJobStatistics in the database.</p>
	 * 
	 * @return all BatchJobStatistics in the database
	 */
	public List<BatchJobStatistic> findAll() {
		return dao.findAll();
	}

	/**
	 * <p>Returns all BatchJobStatistics in the database that
	 * match the specified search criteria.</p>
	 * 
	 * @param batchJobStatistic an example BatchJobStatistic
	 * @return all BatchJobStatistics in the database that
	 * match the specified search criteria
	 */
	public List<BatchJobStatistic> findByExample(BatchJobStatistic batchJobStatistic) {
		return dao.findByExample(batchJobStatistic);
	}

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
	public List<BatchJobStatistic> findByProperty(String propertyName, Object propertyValue) {
		return dao.findByProperty(propertyName, propertyValue);
	}

	/**
	 * <p>Returns the BatchJobStatistic with the specified id.</p>
	 * 
	 * @param id the id of the requested batchJobStatistic
	 * @return the BatchJobStatistic with the specified id
	 */
	public BatchJobStatistic findById(Integer id) {
		return dao.findById(id);
	}

	/**
	 * <p>Saves the BatchJobStatistic passed.</p>
	 * 
	 * @param batchJobStatistic the batchJobStatistic to save
	 */
	public void save(BatchJobStatistic batchJobStatistic) {
		dao.save(batchJobStatistic);
	}

	/**
	 * <p>Deletes the BatchJobStatistic with the specified id.</p>
	 * 
	 * @param batchJobStatistic the batchJobStatistic to delete
	 */
	public void delete(BatchJobStatistic batchJobStatistic) {
		dao.delete(batchJobStatistic);
	}

	/**
	 * <p>Sets the BatchJobStatisticDao.</p>
	 * 
	 * @param batchJobStatisticDao the batchJobStatisticDao to set
	 */
	public void setBatchJobStatisticDao(BatchJobStatisticDao dao) {
		this.dao = dao;
	}
}
